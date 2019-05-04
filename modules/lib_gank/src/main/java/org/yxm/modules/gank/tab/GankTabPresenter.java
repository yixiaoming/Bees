package org.yxm.modules.gank.tab;

import android.content.Context;
import java.util.List;
import org.yxm.modules.base.mvp.BasePresenter;
import org.yxm.modules.gank.entity.GankEntity;
import org.yxm.modules.gank.model.GankModel;
import org.yxm.modules.gank.model.IGankModel;

public class GankTabPresenter extends BasePresenter<IGankTabView> {

  private IGankModel mGankModel;

  public GankTabPresenter(Context context) {
    this.mGankModel = new GankModel(context);
  }

  public void loadData(String type) {
    mGankModel.loadLocalData(type, new IGankModel.LoadDataListener() {

      @Override
      public void onSuccess(List<GankEntity> contents) {
        if (mView.get() != null) {
          mView.get().onInitDataSuccess(contents);
        }
      }

      @Override
      public void onFailed(Throwable e) {
        if (mView.get() != null) {
          mView.get().onInitDataFailed(e);
        }
      }
    });
  }

  public void doRefresh(String type) {
    mGankModel.loadNetData(type, new IGankModel.LoadDataListener() {

      @Override
      public void onSuccess(List<GankEntity> contents) {
        if (mView.get() != null) {
          mView.get().onRefreshSuccess(contents);
        }
      }

      @Override
      public void onFailed(Throwable e) {
        if (mView.get() != null) {
          mView.get().onRefreshFailed(e);
        }
      }
    });
  }

  public void doLoadmore(String type) {
    mGankModel.loadNetData(type, new IGankModel.LoadDataListener() {

      @Override
      public void onSuccess(List<GankEntity> contents) {
        if (mView.get() != null) {
          mView.get().onLoadMoreSuccess(contents);
        }
      }

      @Override
      public void onFailed(Throwable e) {
        if (mView.get() != null) {
          mView.get().onLoadMoreFailed(e);
        }
      }
    });
  }
}
