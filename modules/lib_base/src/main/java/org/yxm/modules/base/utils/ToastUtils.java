package org.yxm.modules.base.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by yixiaoming on 2018/4/6.
 */

public class ToastUtils {

  public static void s(Context context, int strid) {
    Toast.makeText(context, strid, Toast.LENGTH_SHORT).show();
  }

  public static void s(Context context, String msg) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
  }

  public static void l(Context context, int strid) {
    Toast.makeText(context, strid, Toast.LENGTH_LONG).show();
  }

  public static void l(Context context, String msg) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
  }
}
