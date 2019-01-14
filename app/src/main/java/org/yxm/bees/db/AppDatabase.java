package org.yxm.bees.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import org.yxm.bees.base.BeesApp;
import org.yxm.bees.conversion.DateConversionFactory;
import org.yxm.bees.db.dao.KaiyanDao;
import org.yxm.bees.module.wanandroid.repo.local.IWanDao;
import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.db.dao.GankDao;
import org.yxm.bees.entity.kaiyan.KaiyanCategory;
import org.yxm.bees.entity.kaiyan.KaiyanVideoItem;
import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.entity.wan.WanTabEntity;

@Database(entities = {GankEntity.class, KaiyanCategory.class, KaiyanVideoItem.class,
        WanTabEntity.class, WanArticleEntity.class}, version = 1)
@TypeConverters({DateConversionFactory.class})
public abstract class AppDatabase extends RoomDatabase {
    public static final String TAG = "AppDatabase";

    public static final String DB_NAME = "bees.db";

    private static AppDatabase sInstance;

    public abstract GankDao getGankDao();

    public abstract KaiyanDao getKaiyanDao();

    public abstract IWanDao getWanDao();

    public static AppDatabase getInstance() {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(BeesApp.getInstance().getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DB_NAME).build();
    }


}
