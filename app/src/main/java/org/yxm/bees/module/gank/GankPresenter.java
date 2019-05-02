package org.yxm.bees.module.gank;

import org.yxm.bees.model.impl.GankModel;
import org.yxm.bees.model.IGankModel;
import org.yxm.modules.base.BasePresenter;

public class GankPresenter extends BasePresenter<IGankView> {

    private IGankModel mGankModel;

    public GankPresenter() {
        mGankModel = new GankModel();
    }

    public void loadData() {
        mView.get().initDataView(mGankModel.getDefaultTabs());
    }
}
