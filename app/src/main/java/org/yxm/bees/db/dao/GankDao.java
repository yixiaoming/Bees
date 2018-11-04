package org.yxm.bees.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.yxm.bees.entity.gankio.GankEntity;

import java.util.List;

@Dao
public interface GankDao {

    String TABLE_NAME = "t_gank";

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE type=:type LIMIT 15")
    List<GankEntity> getLastDatas(String type);

    @Insert
    void insertAll(List<GankEntity> gankEntitys);

}
