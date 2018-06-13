package org.yxm.bees.util;

import android.util.Log;

import org.yxm.bees.config.AppConfig;

/**
 * Created by yixiaoming on 2018/4/6.
 */

public class LogUtil {

    private static final boolean DEBUG = AppConfig.DEBUG;
    public static String TAG = "Bees";

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }
}
