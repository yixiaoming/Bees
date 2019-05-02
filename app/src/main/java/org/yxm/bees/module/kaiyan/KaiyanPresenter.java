package org.yxm.bees.module.kaiyan;

import org.yxm.bees.entity.kaiyan.KaiyanCategory;
import org.yxm.bees.model.IKaiyanModel;
import org.yxm.bees.model.impl.KaiyanModel;

import java.util.List;
import org.yxm.modules.base.BasePresenter;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanPresenter extends BasePresenter<IKaiyanView> {

    private IKaiyanModel kaiyanModel;

    public KaiyanPresenter() {
        kaiyanModel = new KaiyanModel();
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
