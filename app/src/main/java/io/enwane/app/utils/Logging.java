package io.enwane.app.utils;

import android.util.Log;

public class Logging {
    public static void e(String TAG, String errorMessage) {
        Log.e(TAG, errorMessage);
    }

    public static void d(String TAG, String debugMessage) {
        Log.d(TAG, debugMessage);
    }

    public static void v(String TAG, String verboseMessage) {
        Log.v(TAG, verboseMessage);
    }

    public static void i(String TAG, String infoMessage) {
        Log.i(TAG, infoMessage);
    }

    public static void w(String TAG, String warningMessage) {
        Log.w(TAG, warningMessage);
    }
}
