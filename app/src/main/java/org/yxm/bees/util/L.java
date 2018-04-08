package org.yxm.bees.util;

import android.util.Log;

import org.yxm.bees.config.AppConfig;

/**
 * Created by yixiaoming on 2018/4/6.
 */

public class L {

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
}
