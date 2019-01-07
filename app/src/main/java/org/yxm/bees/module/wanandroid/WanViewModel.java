package org.yxm.bees.module.wanandroid;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;

import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.entity.wan.WanTabEntity;
import org.yxm.bees.model.IWanModel;
import org.yxm.bees.model.impl.WanModel;
import org.yxm.lib.async.ThreadManager;

import java.util.List;

public class WanViewModel extends ViewModel {

    private MutableLiveData<List<WanTabEntity>> mWanTabLiveData;
    private MutableLiveData<List<WanArticleEntity>> mWanArticleLiveData;

    public LiveData<List<WanTabEntity>> getWanTabLiveData() {
        if (mWanTabLiveData == null) {
            mWanTabLiveData = new MutableLiveData<>();
            loadWanTabs();
        }
        return mWanTabLiveData;
    }

    private void loadWanTabs() {
        ThreadManager.getInstance().runIo(() -> {
            IWanModel mModel = new WanModel();
            List<WanTabEntity> tabs = mModel.getWanTabs();
            ThreadManager.getInstance().runOnUiThread(() -> {
                if (tabs != null) {
                    mWanTabLiveData.setValue(tabs);
                }
            });
        });
    }

    public LiveData<List<WanArticleEntity>> getWanArticleLiveData(int authorId) {
        if (mWanArticleLiveData == null) {
            mWanArticleLiveData = new MutableLiveData<>();
        }
        return mWanArticleLiveData;
    }

    public void loadWanArticles(int authorId, int page, SwipeRefreshLayout swipeRefreshLayout) {
        IWanModel mModel = new WanModel();
        mModel.asyncGetWanArticleDatas(authorId, new IWanModel.LoadDataListener<List<WanArticleEntity>>() {
            @Override
            public void onSuccess(int code, List<WanArticleEntity> datas) {
                if (datas != null && datas.size() > 0) {
                    mWanArticleLiveData.setValue(datas);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFialed(int code, Throwable throwable) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, page);
    }

    public void onRefreshArticles(int authorId,
                                  int page,
                                  SwipeRefreshLayout swipeRefreshLayout) {
        IWanModel mModel = new WanModel();
        mModel.asyncGetWanArticleDatas(authorId, new IWanModel.LoadDataListener<List<WanArticleEntity>>() {
            @Override
            public void onSuccess(int code, List<WanArticleEntity> datas) {
                if (datas != null && datas.size() > 0) {
                    List<WanArticleEntity> finalDatas = mWanArticleLiveData.getValue();
                    finalDatas.addAll(0, datas);
                    mWanArticleLiveData.setValue(finalDatas);
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFialed(int code, Throwable throwable) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, page);

    }
}
