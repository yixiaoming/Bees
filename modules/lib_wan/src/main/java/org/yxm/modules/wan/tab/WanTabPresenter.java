package org.yxm.modules.wan.tab;

import java.util.List;
import org.yxm.lib.async.ThreadManager;
import org.yxm.modules.base.mvp.BasePresenter;
import org.yxm.modules.wan.entity.WanArticleEntity;
import org.yxm.modules.wan.repo.local.IWanModel;
import org.yxm.modules.wan.repo.local.WanModel;

public class WanTabPresenter extends BasePresenter<IWanTabView> {

  private IWanModel mWanModel;

  WanTabPresenter() {
    if (mWanModel == null) {
      mWanModel = new WanModel();
    }
  }

  public void startLoadData(int authorId, int page) {
    mWanModel.asyncGetWanArticleDatas(authorId,
        new IWanModel.LoadDataListener<List<WanArticleEntity>>() {
          @Override
          public void onSuccess(int code, final List<WanArticleEntity> datas) {
            ThreadManager.getInstance().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                mView.get().onLoadDataFinish(datas);
              }
            });
          }

          @Override
          public void onFialed(int code, Throwable throwable) {

          }
        }, page);
  }
}
