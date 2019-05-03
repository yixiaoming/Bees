package org.yxm.modules.wan.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import java.util.List;

public class WanArticleWithTag {

    @Embedded
    public WanArticleEntity article;

    @Relation(parentColumn = "id", entityColumn = "article_id")
    public List<Tag> tags;

    @Override
    public String toString() {
        return "WanArticleWithTag{" +
                "article=" + article +
                ", tags=" + tags +
                '}';
    }
}
