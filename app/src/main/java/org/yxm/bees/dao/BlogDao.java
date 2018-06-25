package org.yxm.bees.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.yxm.bees.entity.Blog;

import java.util.List;

@Dao
public interface BlogDao {

    @Query("SELECT * FROM blog")
    List<Blog> list();

    @Query("SELECT * FROM blog WHERE id IN (:ids)")
    List<Blog> findByIds(int[] ids);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(List<Blog> blogs);

    @Update()
    void update(Blog blog);

    @Delete()
    void delete(Blog blog);
}
