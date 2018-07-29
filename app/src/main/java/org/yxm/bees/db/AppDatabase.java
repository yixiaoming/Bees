package org.yxm.bees.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import org.yxm.bees.base.BeesApp;
import org.yxm.bees.db.conversion.DateConversionFactory;
import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.db.dao.GankDao;

@Database(entities = {GankEntity.class}, version = 1)
@TypeConverters({DateConversionFactory.class})
public abstract class AppDatabase extends RoomDatabase {
    public static final String TAG = "AppDatabase";

    public static final String DB_NAME = "bees.db";

    private static AppDatabase sInstance;

    public abstract GankDao gankDao();

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
