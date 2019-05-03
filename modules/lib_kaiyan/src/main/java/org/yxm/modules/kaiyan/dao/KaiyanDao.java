package org.yxm.modules.kaiyan.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;
import org.yxm.modules.kaiyan.entity.KaiyanCategory;
import org.yxm.modules.kaiyan.entity.KaiyanVideoItem;

/**
 * Created by yxm on 2018.7.29.
 */

@Dao
public interface KaiyanDao {

    String CATETORY_TABLE_NAME = "t_kaiyan_category";
    String VIDEO_TABLE_NAME = "t_kaiyan_video";

    @Query("SELECT * FROM " + CATETORY_TABLE_NAME)
    List<KaiyanCategory> getCategoryes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCatetories(List<KaiyanCategory> categories);

    @Query("SELECT * FROM " + VIDEO_TABLE_NAME + " WHERE tab_id=:typeId LIMIT 15")
    List<KaiyanVideoItem> getVideos(int typeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertVideos(List<KaiyanVideoItem> items);
}
