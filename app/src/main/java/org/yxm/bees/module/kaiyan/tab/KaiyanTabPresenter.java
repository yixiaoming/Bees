package org.yxm.bees.module.kaiyan.tab;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.entity.kaiyan.KaiyanVideoItem;
import org.yxm.bees.model.IKaiyanModel;
import org.yxm.bees.model.KaiyanModel;

import java.util.List;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanTabPresenter extends BasePresenter<IKaiyanTabView> {

    private IKaiyanModel mModel;

    public KaiyanTabPresenter() {
        mModel = new KaiyanModel();
    }

    public void initLocalData(int tabid) {
        mModel.loadLocalVideos(tabid, new IKaiyanModel.LoadDataListener<List<KaiyanVideoItem>>() {
            @Override
            public void onSuccess(List<KaiyanVideoItem> datas) {
                if (mView.get() != null) {
                    mView.get().initLocalDataSuccess(datas);
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

    public void loadLocalDataFailed(int tabId) {
        mModel.loadNetVideos(tabId, new IKaiyanModel.LoadDataListener<List<KaiyanVideoItem>>() {

            @Override
            public void onSuccess(List<KaiyanVideoItem> data) {
                if (mView.get() != null) {
                    mView.get().initLocalDataSuccess(data);
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

    public void onRefresh(int tabId) {
        mModel.loadNextPageVideos(tabId, new IKaiyanModel.LoadDataListener<List<KaiyanVideoItem>>() {

            @Override
            public void onSuccess(List<KaiyanVideoItem> datas) {
                if (mView.get() != null) {
                    mView.get().doRefreshSuccess(datas);
                }
            }

            @Override
            public void onFailed(Throwable t) {
                if (mView.get() != null) {
                    mView.get().doRefreshFailed(t);
                }
            }
        });
    }
}
