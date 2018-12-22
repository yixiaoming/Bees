package org.yxm.bees.entity.wan;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "t_wantab")
public class WanTabEntity {

    @PrimaryKey
    public int id;
    @ColumnInfo(name = "course_id")
    public int courseId;
    public String name;
    public int order;
    public int visible;

    @Override
    public String toString() {
        return "WanTabEntity{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", visible=" + visible +
                '}';
    }
}
