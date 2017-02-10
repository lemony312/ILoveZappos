package com.example.louis.ilovezappos.Util;

import android.util.Log;

import com.example.louis.ilovezappos.app.Config;

/**
 * Created by Louis on 2/2/2017.
 */

public class L {
    public static boolean LOGGING_ENABLED = Config.DEBUG;

    public static void d(final String tag, String message) {
        if (LOGGING_ENABLED){
            Log.d(tag, message);
        }
    }

    public static void d(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED){
            Log.d(tag, message, cause);
        }
    }

    public static void v(final String tag, String message) {
        if (LOGGING_ENABLED) {
            Log.v(tag, message);
        }
    }

    public static void v(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.v(tag, message, cause);
        }
    }

    public static void i(final String tag, String message) {
        if (LOGGING_ENABLED) {
            Log.i(tag, message);
        }
    }

    public static void i(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.i(tag, message, cause);
        }
    }

    public static void w(final String tag, String message) {
        if (LOGGING_ENABLED) {
            Log.w(tag, message);
        }
    }

    public static void w(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.w(tag, message, cause);
        }
    }

    public static void e(final String tag, String message) {
        if (LOGGING_ENABLED){
            Log.e(tag, message);
        }
    }

    public static void e(final String tag, String message, Throwable cause) {
        if (LOGGING_ENABLED) {
            Log.e(tag, message, cause);
        }
    }

    public static void tmp(String message) {
        if (LOGGING_ENABLED) {
            Log.i(">>>>>>>>>>>>>>>>>>>>>>>", message + "");
        }
    }

    private L() {
    }
}
