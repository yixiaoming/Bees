package org.yxm.modules.gank.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import org.yxm.modules.base.conversion.ListStringConversion;

@Entity(tableName = "t_gank")
public class GankEntity {

  @NonNull
  @PrimaryKey(autoGenerate = true)
  public Integer id;

  @SerializedName("_id")
  public String gankid;
  @ColumnInfo(name = "create_at")
  public String createdAt;
  public String desc;
  @ColumnInfo(name = "published_at")
  public String publishedAt;
  public String source;
  public String type;
  public String url;
  public Boolean used;
  public String who;
  @TypeConverters(ListStringConversion.class)
  public List<String> images;

  @Override
  public String toString() {
    return "GankEntity{" +
        "id='" + id + '\'' +
        ", gankid='" + gankid + '\'' +
        ", createdAt='" + createdAt + '\'' +
        ", desc='" + desc + '\'' +
        ", publishedAt='" + publishedAt + '\'' +
        ", source='" + source + '\'' +
        ", type='" + type + '\'' +
        ", url='" + url + '\'' +
        ", used=" + used +
        ", who='" + who + '\'' +
        ", images=" + images +
        '}';
  }
}
