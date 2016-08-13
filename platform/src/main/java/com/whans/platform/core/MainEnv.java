package com.whans.platform.core;

import android.content.Context;
import android.content.Intent;

/**
 * @author hanson.
 */

public final class MainEnv {
    private static Intent loginActivityIntent;
    private static Context activityContext;

    // please call this init main activity with the activity context
    public static void init(Context context, Intent loginIntent) {
        activityContext = context;
        loginActivityIntent = loginIntent;
        loginActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);

    }

    public static void startLoginActivity() {
        if (activityContext != null && loginActivityIntent != null) {
            activityContext.startActivity(loginActivityIntent);
        }
    }
}
