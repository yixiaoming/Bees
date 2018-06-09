package org.yxm.bees.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by yixiaoming on 2018/4/6.
 */

public class ToastUtil {

    public static void s(Context context, @StringRes int strid) {
        String msg = context.getResources().getString(strid);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void s(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void l(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
