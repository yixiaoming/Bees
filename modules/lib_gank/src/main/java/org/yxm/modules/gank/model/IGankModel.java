package org.yxm.modules.gank.model;


import java.util.List;
import org.yxm.modules.gank.entity.GankEntity;
import org.yxm.modules.gank.entity.GankTabEntity;

public interface IGankModel {

  List<GankTabEntity> getDefaultTabs();

  void loadLocalData(String type, LoadDataListener listener);

  void loadNetData(String type, LoadDataListener listener);

  interface LoadDataListener {

    void onSuccess(List<GankEntity> contents);

    void onFailed(Throwable e);
  }
}
