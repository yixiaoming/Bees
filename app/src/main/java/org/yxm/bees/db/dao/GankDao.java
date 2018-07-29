package org.yxm.bees.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.yxm.bees.entity.gankio.GankEntity;

import java.util.List;

@Dao
public interface GankDao {

    public static final String TABLE_NAME = "t_gank";

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE type=:type LIMIT 20")
    List<GankEntity> getLastDatas(String type);

    @Insert
    void insertAll(List<GankEntity> gankEntitys);

}
