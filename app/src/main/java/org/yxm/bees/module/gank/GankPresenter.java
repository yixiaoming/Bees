package org.yxm.bees.module.gank;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.model.GankModel;
import org.yxm.bees.model.IGankModel;

public class GankPresenter extends BasePresenter<IGankView> {

    private IGankModel mGankModel;

    public GankPresenter() {
        mGankModel = new GankModel();
    }

    public void loadData() {
        mView.get().initDataView(mGankModel.getDefaultTabs());
    }
}
