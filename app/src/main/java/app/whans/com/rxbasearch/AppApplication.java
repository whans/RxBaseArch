package app.whans.com.rxbasearch;

import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

/**
 * @author whans.
 */

@ReportsCrashes(
        httpMethod = HttpSender.Method.PUT,
        reportType = HttpSender.Type.JSON
//        formUri = BuildConfig.DEFAULT_ACRA_URL,
//        formUriBasicAuthLogin = BuildConfig.DEFAULT_ACRA_USER,
//        formUriBasicAuthPassword = BuildConfig.DEFAULT_ACRA_PASSWORD
)
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (!BuildConfig.DEBUG) {
            ACRA.init(this);
        }
    }
}
