package org.yxm.modules.kaiyan.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import org.yxm.modules.base.conversion.DateConversionFactory;
import org.yxm.modules.kaiyan.dao.KaiyanDao;
import org.yxm.modules.kaiyan.entity.KaiyanCategory;
import org.yxm.modules.kaiyan.entity.KaiyanVideoItem;

@Database(entities = {KaiyanCategory.class, KaiyanVideoItem.class}, version = 1)
@TypeConverters({DateConversionFactory.class})
public abstract class AppDatabase extends RoomDatabase {

  public static final String TAG = "AppDatabase";

  public static final String DB_NAME = "kaiyan.db";

  private static AppDatabase sInstance;

  public abstract KaiyanDao getKaiyanDao();

  public static AppDatabase getInstance(Context context) {
    if (sInstance == null) {
      synchronized (AppDatabase.class) {
        if (sInstance == null) {
          sInstance = buildDatabase(context.getApplicationContext());
        }
      }
    }
    return sInstance;
  }

  private static AppDatabase buildDatabase(Context appContext) {
    return Room.databaseBuilder(appContext, AppDatabase.class, DB_NAME).build();
  }


}
