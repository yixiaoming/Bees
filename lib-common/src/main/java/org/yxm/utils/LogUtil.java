package org.yxm.utils;

import android.util.Log;

/**
 * Created by yixiaoming on 2018/4/6.
 */

public class LogUtil {

    private static final boolean DEBUG = true;
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
