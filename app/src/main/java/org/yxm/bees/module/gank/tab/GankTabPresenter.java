package org.yxm.bees.module.gank.tab;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.model.GankModel;
import org.yxm.bees.model.IGankModel;

import java.util.List;

public class GankTabPresenter extends BasePresenter<IGankTabView> {

    private IGankModel mGankModel;

    public GankTabPresenter() {
        this.mGankModel = new GankModel();
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
            public void onFailed(Exception e) {
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
            public void onFailed(Exception e) {
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
            public void onFailed(Exception e) {
                if (mView.get() != null) {
                    mView.get().onLoadMoreFailed(e);
                }
            }
        });
    }
}
