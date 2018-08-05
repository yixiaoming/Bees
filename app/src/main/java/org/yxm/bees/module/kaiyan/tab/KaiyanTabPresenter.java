package org.yxm.bees.module.kaiyan.tab;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.entity.kaiyan.KaiyanVideoItem;
import org.yxm.bees.model.IKaiyanModel;
import org.yxm.bees.model.impl.KaiyanModel;

import java.util.List;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanTabPresenter extends BasePresenter<IKaiyanTabView> {

    private IKaiyanModel mModel;

    public KaiyanTabPresenter() {
        mModel = new KaiyanModel();
    }

    public void doInitLocalData(int tabid) {
        mModel.loadLocalVideos(tabid, new IKaiyanModel.LoadDataListener<List<KaiyanVideoItem>>() {
            @Override
            public void onSuccess(List<KaiyanVideoItem> datas) {
                if (mView.get() != null) {
                    mView.get().onInitLocalDataSuccess(datas);
                }
            }

            @Override
            public void onFailed(Throwable t) {
                if (mView.get() != null) {
                    mView.get().onInitLocalDataFailed(t);
                }
            }
        });
    }

    public void doLoadNetData(int tabId) {
        mModel.loadNetVideos(tabId, new IKaiyanModel.LoadDataListener<List<KaiyanVideoItem>>() {

            @Override
            public void onSuccess(List<KaiyanVideoItem> data) {
                if (mView.get() != null) {
                    mView.get().onInitNetDataSuccess(data);
                }
            }

            @Override
            public void onFailed(Throwable t) {
                if (mView.get() != null) {
                    mView.get().onInitNetDataFailed(t);
                }
            }
        });
    }

    public void doRefresh(int tabId) {
        mModel.loadNextPageVideos(tabId, new IKaiyanModel.LoadDataListener<List<KaiyanVideoItem>>() {

            @Override
            public void onSuccess(List<KaiyanVideoItem> datas) {
                if (mView.get() != null) {
                    mView.get().onRefreshSuccess(datas);
                }
            }

            @Override
            public void onFailed(Throwable t) {
                if (mView.get() != null) {
                    mView.get().onRefreshFailed(t);
                }
            }
        });
    }


    public void doLoadmore(int tabId) {
        mModel.loadNextPageVideos(tabId, new IKaiyanModel.LoadDataListener<List<KaiyanVideoItem>>() {

            @Override
            public void onSuccess(List<KaiyanVideoItem> datas) {
                if (mView.get() != null) {
                    mView.get().onLoadmoreSuccess(datas);
                }
            }

            @Override
            public void onFailed(Throwable t) {
                if (mView.get() != null) {
                    mView.get().onLoadmoreFailed(t);
                }
            }
        });
    }
}
