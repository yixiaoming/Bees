package org.yxm.bees.base;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import javax.inject.Inject;
import org.yxm.bees.module.main.DaggerMainActivityComponent;
import org.yxm.bees.module.main.MainActivityComponent;
import org.yxm.components.runtime.AppRuntime;
import org.yxm.lib.volley.VolleyManager;

/**
 * Created by yixiaoming on 2018/4/6.
 */

public class BeesApp extends Application {

  private static BeesApp instance;

  static MainActivityComponent mainActivityComponent;

  @Inject
  Gson gson;

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    instance = this;

    AppRuntime.init(instance);

    mainActivityComponent = DaggerMainActivityComponent.create();

    VolleyManager.init(this);
  }

  public static BeesApp getInstance() {
    return instance;
  }

  public static MainActivityComponent getComponent() {
    return mainActivityComponent;
  }
}
