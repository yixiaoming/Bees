package org.yxm.modules.kaiyan.entity;

import java.util.List;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanVideoList {

  public List<KaiyanVideoItem> itemList;
  public String nextPageUrl;

  @Override
  public String toString() {
    return "KaiyanVideoList{" +
        "itemList=" + itemList +
        ", nextPageUrl='" + nextPageUrl + '\'' +
        '}';
  }
}
