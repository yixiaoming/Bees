package org.yxm.modules.wan.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import org.yxm.modules.wan.entity.Tag;

@Dao
public interface TagDao {

    @Insert
    void insertTags(List<Tag> tags);

    @Query("SELECT * FROM Tag WHERE article_id=:articleId")
    void getTagsWithArticleId(int articleId);

}
