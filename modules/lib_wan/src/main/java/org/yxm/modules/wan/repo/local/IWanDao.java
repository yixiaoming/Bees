package org.yxm.modules.wan.repo.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;
import org.yxm.modules.wan.entity.WanArticleEntity;
import org.yxm.modules.wan.entity.WanTabEntity;

@Dao
public interface IWanDao {

  String WAN_TAB_TABLE_NAME = "t_wan_tab";
  String WAN_ARTICLE_TABLE_NAME = "t_wan_article";

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertWanTabs(List<WanTabEntity> datas);

  @Query(value = "SELECT * FROM " + WAN_TAB_TABLE_NAME)
  List<WanTabEntity> getWantabs();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertWanArticles(List<WanArticleEntity> datas);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertWanArticle(WanArticleEntity entity);

  @Query(value = "SELECT * FROM " + WAN_ARTICLE_TABLE_NAME + " WHERE id=:id")
  WanArticleEntity findWanArticleEntityById(int id);

  @Query(value = "SELECT * FROM " + WAN_ARTICLE_TABLE_NAME
      + " WHERE chapter_id =:chapterId limit :size")
  List<WanArticleEntity> getWanArticles(int chapterId, int size);
}
