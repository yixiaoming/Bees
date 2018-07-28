package org.yxm.bees.module.news.tab;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.model.GankModel;
import org.yxm.bees.model.IGankModel;

import java.util.List;

public class NewsTabPresenter extends BasePresenter<INewsTabView> {

    private IGankModel mGankModel;

    public NewsTabPresenter() {
        this.mGankModel = new GankModel();
    }

    public void loadData(String type) {
        mGankModel.loadLocalData(type, new IGankModel.LoadDataListener() {

            @Override
            public void onSuccess(List<GankEntity> contents) {
                if (mView.get() != null) {
                    mView.get().initDatas(contents);
                }
            }

            @Override
            public void onFailed(Exception e) {
                if (mView.get() != null) {
                    mView.get().initDatasFailed(e);
                }
            }
        });
    }

    public void onRefresh(String type) {
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

    public void onLoadMore(String type) {
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