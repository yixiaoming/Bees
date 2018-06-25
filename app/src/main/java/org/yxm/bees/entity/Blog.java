package org.yxm.bees.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "blog")
public class Blog {
    @PrimaryKey
    public int id;

    public String author;

    public String title;

    public String content;

    public Date date;

    public Blog(Integer id, String author, String title, String content, Date date) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Blog{" +
                ", id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
