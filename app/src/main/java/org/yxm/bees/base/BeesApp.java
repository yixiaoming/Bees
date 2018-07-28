package org.yxm.bees.base;

import android.app.Application;
import android.content.Context;

import org.yxm.bees.db.AppDatabase;

/**
 * Created by yixiaoming on 2018/4/6.
 */

public class BeesApp extends Application {

    private static BeesApp instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
    }

    public static BeesApp getInstance() {
        return instance;
    }
}