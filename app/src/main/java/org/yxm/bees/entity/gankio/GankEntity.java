package org.yxm.bees.entity.gankio;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GankEntity {

    @SerializedName("_id")
    public String id;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public Boolean used;
    public String who;
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
