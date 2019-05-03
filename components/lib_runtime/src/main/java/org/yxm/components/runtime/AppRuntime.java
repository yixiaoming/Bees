package org.yxm.components.runtime;

import android.app.Application;
import android.content.Context;

/**
 * 用于App的Context对象
 *
 * @author yixiaoming
 */
public class AppRuntime {

  private static volatile Context sAppContext;

  /**
   * app启动过程中初始化
   */
  public static void init(Context context) {
    sAppContext = context;
  }

  public static Application getApp() {
    return (Application) sAppContext;
  }

  public static Context getContext() {
    return sAppContext;
  }
}
