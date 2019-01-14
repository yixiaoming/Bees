package org.yxm.bees.entity.wan;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "t_wan_article")
public class WanArticleEntity {
    @PrimaryKey
    public int id;
    public String author;
    @ColumnInfo(name = "chapter_id")
    public int chapterId;
    @ColumnInfo(name = "chapter_name")
    public String chapterName;
    public boolean collect;
    public String desc;
    @ColumnInfo(name = "envelope_pic")
    public String envelopePic;
    public boolean fresh;
    public String link;
    @ColumnInfo(name = "nice_date")
    public String niceDate;
    public String origin;
    @ColumnInfo(name = "publish_time")
    public long publishTime;
    public String title;
    public int zan;

    @Ignore
    public List<Tag> tags;

    @Override
    public String toString() {
        return "WanArticleEntity{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", desc='" + desc + '\'' +
                ", link='" + link + '\'' +
                ", niceDate='" + niceDate + '\'' +
                ", origin='" + origin + '\'' +
                ", publishTime=" + publishTime +
                ", tags=" + tags +
                ", title='" + title + '\'' +
                ", zan=" + zan +
                '}';
    }
}
