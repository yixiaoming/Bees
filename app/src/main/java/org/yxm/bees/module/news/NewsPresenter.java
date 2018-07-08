package org.yxm.bees.module.news;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.model.GankModel;
import org.yxm.bees.model.IGankModel;

public class NewsPresenter extends BasePresenter<INewsView> {

    private IGankModel mGankModel;

    public NewsPresenter() {
        mGankModel = new GankModel();
    }

    public void loadData() {
        mView.get().initDataView(mGankModel.getDefaultTabs());
    }
}
