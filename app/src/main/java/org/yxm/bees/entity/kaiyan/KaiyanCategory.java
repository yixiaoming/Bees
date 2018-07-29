package org.yxm.bees.entity.kaiyan;

import android.arch.persistence.room.PrimaryKey;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanCategory {
    @PrimaryKey
    public Integer id;
    public String name;
    public String description;
    public String bgPicture;
    public String headerImage;
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
