package org.yxm.bees.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.yxm.bees.entity.wan.Tag;
import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.entity.wan.WanTabEntity;

import java.util.List;

@Dao
public interface TagDao {

    @Insert
    void insertTags(List<Tag> tags);

    @Query("SELECT * FROM Tag WHERE article_id=:articleId")
    void getTagsWithArticleId(int articleId);

}
