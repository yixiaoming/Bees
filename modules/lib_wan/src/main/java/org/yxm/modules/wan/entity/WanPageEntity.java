package org.yxm.modules.wan.entity;

import java.util.List;

public class WanPageEntity<T> {

  public int curPage;
  public int offset;
  public boolean over;
  public int pageCount;
  public int size;
  public int total;

  public List<T> datas;
}

