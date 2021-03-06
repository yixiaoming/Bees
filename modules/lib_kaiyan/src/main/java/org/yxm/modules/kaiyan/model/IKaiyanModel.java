package org.yxm.modules.kaiyan.model;

/**
 * Created by yxm on 2018.7.29.
 */

public interface IKaiyanModel {

  void loadLocalCatetories(LoadDataListener listener);

  void loadNetCategories(LoadDataListener listener);

  void loadNextPageVideos(int tabId, LoadDataListener listener);

  interface LoadDataListener<T> {

    void onSuccess(T data);

    void onFailed(Throwable t);
  }

  void loadLocalVideos(int tabid, LoadDataListener listener);

  void loadNetVideos(int tabid, LoadDataListener listener);
}
