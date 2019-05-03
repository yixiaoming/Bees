package org.yxm.modules.kaiyan.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by yxm on 2018.7.29.
 */

@Entity(tableName = "t_kaiyan_category")
public class KaiyanCategory {
    @NonNull
    @PrimaryKey
    public Integer id;
    public String name;
    public String description;
    @ColumnInfo(name = "bg_picture")
    public String bgPicture;
    @ColumnInfo(name = "header_image")
    public String headerImage;
    @ColumnInfo(name = "author_id")
    public String defaultAuthorId;

    @Override
    public String toString() {
        return "KaiyanCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", bgPicture='" + bgPicture + '\'' +
                ", headerImage='" + headerImage + '\'' +
                ", defaultAuthorId='" + defaultAuthorId + '\'' +
                '}';
    }
}
