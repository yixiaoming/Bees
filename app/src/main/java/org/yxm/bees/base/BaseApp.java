package org.yxm.bees.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by yixiaoming on 2018/4/6.
 */

public class BaseApp extends Application {

    private static BaseApp instance;

    private BaseApp() {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static BaseApp getInstance() {
        if (instance == null) {
            synchronized (BaseApp.class) {
                instance = new BaseApp();
            }
        }
        return instance;
    }
}
