package com.whans.platform.utils;

import org.acra.ACRA;
/**
 * @author hanson.
 */

public final class Logger {
    public static final int LOG_LEVEL_FULL = 1;
    public static final int LOG_LEVEL_NONE = 0;

    public static void init(String logTag, int level) {
        switch (level) {
            case LOG_LEVEL_FULL:
                com.orhanobut.logger.Logger.init(logTag)
                        .setLogLevel(LogLevel.FULL)
                        .setMethodOffset(1)
                        .setMethodCount(1)
                        .hideThreadInfo();
                break;
            case LOG_LEVEL_NONE:
                com.orhanobut.logger.Logger.init().setLogLevel(LogLevel.NONE);
                break;
            default:
                com.orhanobut.logger.Logger.init().setLogLevel(LogLevel.NONE);
        }
    }

    public static void v(String message, Object... objects) {
        com.orhanobut.logger.Logger.v(message, objects);
    }

    public static void i(String message, Object... objects) {
        com.orhanobut.logger.Logger.i(message, objects);
    }

    public static void d(String message, Object... objects) {
        com.orhanobut.logger.Logger.d(message, objects);
    }

    public static void e(String message, Object... objects) {
        com.orhanobut.logger.Logger.e(message, objects);
        trackBreadcrumb(message);
    }

    public static void w(String message, Object... objects) {
        com.orhanobut.logger.Logger.w(message, objects);
    }

    public static void wtf(String message, Object... objects) {
        com.orhanobut.logger.Logger.wtf(message, objects);
    }

    public static void json(String message) {
        com.orhanobut.logger.Logger.json(message);
    }

    public static void xml(String message) {
        com.orhanobut.logger.Logger.xml(message);
    }

    public static void trackBreadcrumb(String event) {
        if (ACRA.isInitialised()) {
            ACRA.getErrorReporter().putCustomData("Event at " + System.currentTimeMillis(), event);
        }
    }

    public static void handleException(Throwable e) {
        if (ACRA.isInitialised()) {
            ACRA.getErrorReporter().handleException(e);
        }
    }
}
