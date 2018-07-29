package org.yxm.bees.module.kaiyan;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.entity.kaiyan.KaiyanCategory;
import org.yxm.bees.model.IKaiyanModel;
import org.yxm.bees.model.KaiyanModel;

import java.util.List;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanPresenter extends BasePresenter<IKaiyanView> {

    private IKaiyanModel kaiyanModel;

    public KaiyanPresenter() {
        kaiyanModel = new KaiyanModel();
    }

    public void loadPagerFragments() {
        if (mView.get() != null) {
            kaiyanModel.getCategories(new IKaiyanModel.LoadDataListener<List<KaiyanCategory>>() {
                @Override
                public void onSuccess(List<KaiyanCategory> categories) {
                    if (mView.get() != null) {
                        mView.get().initDataSuccess(categories);
                    }
                }

                @Override
                public void onFailed(Throwable t) {
                    if (mView.get() != null) {
                        mView.get().initDataFailed(t);
                    }
                }
            });
        }
    }
}
