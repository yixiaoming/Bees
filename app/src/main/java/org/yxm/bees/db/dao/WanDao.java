package org.yxm.bees.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.entity.wan.WanTabEntity;

import java.util.List;

@Dao
public interface WanDao {

    String WAN_TAB_TABLE_NAME = "t_wantab";
    String WAN_ARTICLE_TABLE_NAME = "t_wanarticle";

    @Insert
    void insertWanTabs(List<WanTabEntity> datas);

    @Query(value = "SELECT * FROM " + WAN_TAB_TABLE_NAME)
    List<WanTabEntity> getWantabs();

    @Insert
    void insertWanArticles(List<WanArticleEntity> datas);

    @Insert
    void insertWanArticle(WanArticleEntity entity);

    @Query(value = "SELECT * FROM " + WAN_ARTICLE_TABLE_NAME + " where id=:id")
    WanArticleEntity findWanArticleEntityById(int id);

    @Query(value = "SELECT * FROM " + WAN_ARTICLE_TABLE_NAME + " WHERE chapter_id =:chapterId limit 10")
    List<WanArticleEntity> getWanArticles(int chapterId);
}
