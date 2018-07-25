package org.yxm.bees.entity.gankio;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.yxm.bees.db.conversion.ListStringConversion;

import java.util.List;

@Entity(tableName = "t_gank")
public class GankEntity {

    @NonNull
    @PrimaryKey
    @SerializedName("_id")
    public String id;
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
