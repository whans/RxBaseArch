package com.whans.platform.core;

import android.text.TextUtils;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.whans.platform.utils.Logger;

import java.net.HttpURLConnection;

import retrofit2.Response;
import rx.Subscriber;

import static com.whans.platform.core.MainEnv.startLoginActivity;

/**
 * @author hanson.
 */
@RxLogSubscriber
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {
        if (isUnsubscribed()) {
            unsubscribe();
        }
        onDone();
    }

    @Override
    public void onError(Throwable e) {
        String message = "Unknown error";
        if (e != null) {
            Logger.handleException(e);
            message = e.getMessage();
            if (TextUtils.isEmpty(message)) {
                onFailed("Network error");
            } else {
                onFailed(message);
            }
        } else {
            onFailed(message);
        }
        onDone();
    }

    @Override
    public void onNext(T t) {
        if (!(t instanceof Response)) {
            throw new ExceptionInInitializerError("please define by Response");
        }

        Response response = ((Response) t);
        if (response.isSuccessful()) {
            onSuccess(t);
        } else {
            switch (response.code()) {
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    startLoginActivity();
                    break;
                default:
                    String error = ServiceFactory.getResponseError(response.errorBody());
                    if (TextUtils.isEmpty(error)) {
                        onFailed("Internal error");
                    } else {
                        onFailed(error);
                    }
            }
        }
    }

    public abstract void onSuccess(T t);

    public abstract void onFailed(String message);

    public abstract void onDone();
}

