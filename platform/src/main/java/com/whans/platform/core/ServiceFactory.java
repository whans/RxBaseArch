package com.whans.platform.core;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.whans.platform.BuildConfig;
import com.whans.platform.utils.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.String.format;

/**
 * @author hanson.
 */
public final class ServiceFactory {
    private static final String TAG = "ServiceFactory";
    private static final String DEFAULT_SERVER_ADDR = "";

    private static final int DEFAULT_TIMEOUT = 6;
    private static String serverAddress = DEFAULT_SERVER_ADDR;
    private static String token;
    private static Retrofit retrofit;
    private Context context;

    public ServiceFactory(Context context) {
        this.context = context;
    }

    public static <T> T createRetrofitService(final Class<T> clazz) {
        return createRetrofitService(clazz, null);
    }

    public static <T> T createRetrofitService(final Class<T> clazz, final String url) {
        if (retrofit == null && TextUtils.isEmpty(url)) {
            retrofit = createRetrofit(serverAddress);
        } else if (!TextUtils.isEmpty(url)) {
            return createRetrofit(url).create(clazz);
        }

        return retrofit.create(clazz);
    }

    public static void updateServerAddress(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (!url.startsWith("http://")) {
                serverAddress = "http://" + url;
            }
            serverAddress = url;
        }
    }

    public static void updateToken(String newToken) {
        token = newToken;
    }

    public static Retrofit createRetrofit(String url) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor((message) -> Log.d(TAG, message));

            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        if (!TextUtils.isEmpty(token)) {
            builder.addInterceptor((chain) -> {
                Request request = chain.request();
                Request newReq = request.newBuilder()
                        .addHeader("Authorization", format("%s", token))
                        .build();

                return chain.proceed(newReq);
            });
        }

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(url)
                .build();

        return retrofit;
    }

    public static String getResponseError(ResponseBody body) {
        if (body != null) {
            ErrorResponse errorResponse = null;
            Gson gson = new Gson();
            try {
                errorResponse = gson.fromJson(body.string(), ErrorResponse.class);
            } catch (IOException e) {
                Logger.handleException(e);
            }

            return errorResponse.error;
        }
        return null;
    }
}

