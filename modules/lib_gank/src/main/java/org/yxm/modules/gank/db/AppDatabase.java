package org.yxm.modules.gank.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import org.yxm.modules.base.conversion.DateConversionFactory;
import org.yxm.modules.gank.dao.GankDao;
import org.yxm.modules.gank.entity.GankEntity;

@Database(entities = {GankEntity.class}, version = 1)
@TypeConverters({DateConversionFactory.class})
public abstract class AppDatabase extends RoomDatabase {

  public static final String TAG = "AppDatabase";

  public static final String DB_NAME = "gank.db";

  private static AppDatabase sInstance;

  public abstract GankDao getGankDao();

  public static AppDatabase getInstance(Context context) {
    if (sInstance == null) {
      synchronized (AppDatabase.class) {
        if (sInstance == null) {
          if (context == null) {
            throw new NullPointerException();
          }
          sInstance = buildDatabase(context.getApplicationContext().getApplicationContext());
        }
      }
    }
    return sInstance;
  }

  private static AppDatabase buildDatabase(Context appContext) {
    return Room.databaseBuilder(appContext, AppDatabase.class, DB_NAME).build();
  }
}
