package org.yxm.modules.gank;

import android.content.Context;
import org.yxm.modules.base.mvp.BasePresenter;
import org.yxm.modules.gank.model.GankModel;
import org.yxm.modules.gank.model.IGankModel;

public class GankPresenter extends BasePresenter<IGankView> {

    private IGankModel mGankModel;

    public GankPresenter(Context context) {
        mGankModel = new GankModel(context);
    }

    public void loadData() {
        mView.get().initDataView(mGankModel.getDefaultTabs());
    }
}
