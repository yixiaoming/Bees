package org.yxm.modules.gank.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import org.yxm.modules.gank.entity.GankEntity;

@Dao
public interface GankDao {

    String TABLE_NAME = "t_gank";

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE type=:type LIMIT 15")
    List<GankEntity> getLastDatas(String type);

    @Insert
    void insertAll(List<GankEntity> gankEntities);

}
