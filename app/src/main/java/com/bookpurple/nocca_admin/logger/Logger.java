package com.bookpurple.nocca_admin.logger;

import android.util.Log;

import com.bookpurple.nocca_admin.BuildConfig;

/**
 * Written by gauravsharma on 2/24/19.
 **/
public class Logger {

    private static final String TAG = Logger.class.getName();

    private static final boolean DEBUG = false;

    private static long previousTime;


    public static void log(Object object) {
        log(TAG, object);
    }

    public static void log(String tag, Object object) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, "" + object);
        }
    }

    public static void log(String tag, String message, Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message, throwable);
        }
    }

    public static void logException(String tag, String reason, Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, reason, throwable);
        }
        //TODO Implement crashlytics / fabric.io
        logException(tag, throwable);
    }

    public static void logException(Throwable throwable) {
        logException(TAG, throwable);
    }

    public static void logException(String tag, Throwable throwable) {
        Log.e(tag, Log.getStackTraceString(throwable));
        //TODO Implement crashlytics / fabric.io
    }

    public static void logTime(String message) {
        long currentTime = System.currentTimeMillis();
        Logger.log("=== Time Logger: " + message + ", currentTime: " + currentTime +
                " ms, Difference: " + (currentTime - previousTime));
        previousTime = currentTime;
    }

    private static boolean isDebuggingEnabled() {
        return true;
    }
}
