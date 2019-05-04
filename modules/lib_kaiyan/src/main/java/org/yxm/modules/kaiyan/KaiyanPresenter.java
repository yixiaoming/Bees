package org.yxm.modules.kaiyan;

import android.content.Context;
import java.util.List;
import org.yxm.modules.base.mvp.BasePresenter;
import org.yxm.modules.kaiyan.entity.KaiyanCategory;
import org.yxm.modules.kaiyan.model.IKaiyanModel;
import org.yxm.modules.kaiyan.model.KaiyanModel;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanPresenter extends BasePresenter<IKaiyanView> {

  private IKaiyanModel kaiyanModel;

  public KaiyanPresenter(Context context) {
    kaiyanModel = new KaiyanModel(context);
  }

  public void loadTabFragments() {
    if (mView.get() != null) {
      kaiyanModel.loadLocalCatetories(new IKaiyanModel.LoadDataListener<List<KaiyanCategory>>() {
        @Override
        public void onSuccess(List<KaiyanCategory> categories) {
          if (mView.get() != null) {
            mView.get().initLocalDataSuccess(categories);
          }
        }

        @Override
        public void onFailed(Throwable t) {
          if (mView.get() != null) {
            mView.get().initLocalDataFailed(t);
          }
        }
      });
    }
  }

  public void loadNetCategories() {
    if (mView.get() != null) {
      kaiyanModel.loadNetCategories(new IKaiyanModel.LoadDataListener<List<KaiyanCategory>>() {
        @Override
        public void onSuccess(List<KaiyanCategory> categories) {
          if (mView.get() != null) {
            mView.get().initLocalDataSuccess(categories);
          }
        }

        @Override
        public void onFailed(Throwable t) {
          if (mView.get() != null) {
            mView.get().initNetDataFailed(t);
          }
        }
      });
    }
  }
}
