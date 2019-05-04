package org.yxm.modules.wan.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = WanArticleEntity.class,
    parentColumns = "id", childColumns = "article_id"))
public class Tag {

  @PrimaryKey(autoGenerate = true)
  public int id;

  public String name;
  public String url;
  @ColumnInfo(name = "article_id")
  public int articleId;

  @Override
  public String toString() {
    return "Tag{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", url='" + url + '\'' +
        ", articleId=" + articleId +
        '}';
  }
}