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
        mGankModel.loadTabContent(type, new IGankModel.LoadTabContentListener() {

            @Override
            public void onSuccess(List<GankEntity> contents) {
                if (mView.get() != null) {
                    mView.get().initDatas(contents);
                }
            }

            @Override
            public void onFailed(Exception e) {
                if (mView.get() != null) {
                    mView.get().showToast("load data failed");
                }
            }
        });
    }

    public void onRefresh(String type) {
        mGankModel.loadTabContent(type, new IGankModel.LoadTabContentListener() {

            @Override
            public void onSuccess(List<GankEntity> contents) {
                if (mView.get() != null) {
                    mView.get().onRefreshDatas(contents);
                }
            }

            @Override
            public void onFailed(Exception e) {
                if (mView.get() != null) {
                    mView.get().showToast("load data failed");
                }
            }
        });
    }

    public void onLoadMore(String type) {
        mGankModel.loadTabContent(type, new IGankModel.LoadTabContentListener() {

            @Override
            public void onSuccess(List<GankEntity> contents) {
                if (mView.get() != null) {
                    mView.get().onLoadMoreDatas(contents);
                }
            }

            @Override
            public void onFailed(Exception e) {
                if (mView.get() != null) {
                    mView.get().showToast("load data failed");
                }
            }
        });
    }
}
