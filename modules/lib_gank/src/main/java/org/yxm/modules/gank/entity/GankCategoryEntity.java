package org.yxm.modules.gank.entity;

import com.google.gson.annotations.SerializedName;

public class GankCategoryEntity {

  @SerializedName("_id")
  public String id;

  @SerializedName("en_name")
  public String enName;

  public String name;

  public Integer rank;

  @Override
  public String toString() {
    return "GankCategoryEntity{" +
        "id='" + id + '\'' +
        ", enName='" + enName + '\'' +
        ", name='" + name + '\'' +
        ", rank=" + rank +
        '}';
  }
}
