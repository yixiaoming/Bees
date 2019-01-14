package org.yxm.bees.module.wanandroid;

import android.app.ActivityManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;

import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.entity.wan.WanTabEntity;
import org.yxm.bees.module.wanandroid.repo.IWanRepo;
import org.yxm.bees.module.wanandroid.repo.WanRepo;
import org.yxm.lib.async.ThreadManager;

import java.util.ArrayList;
import java.util.List;

public class WanViewModel extends ViewModel {

    private MutableLiveData<List<WanTabEntity>> mWanTabLiveData;
    private MutableLiveData<List<WanArticleEntity>> mWanArticleLiveData;

    public LiveData<List<WanTabEntity>> getWanTabLiveData() {
        if (mWanTabLiveData == null) {
            mWanTabLiveData = new MutableLiveData<>();
            initWanTabs();
        }
        return mWanTabLiveData;
    }

    public LiveData<List<WanArticleEntity>> getWanArticleLiveData() {
        if (mWanArticleLiveData == null) {
            mWanArticleLiveData = new MutableLiveData<>();
        }
        return mWanArticleLiveData;
    }

    private void initWanTabs() {
        ThreadManager.getInstance().runIo(() -> {
            IWanRepo repo = new WanRepo();
            List<WanTabEntity> tabs = repo.getWanTabLocalList();
            if (tabs == null || tabs.size() == 0) {
                tabs = repo.getWanTabNetList();
            }
            List<WanTabEntity> finalTabs = tabs;
            ThreadManager.getInstance().runOnUiThread(() -> {
                if (finalTabs != null) {
                    mWanTabLiveData.setValue(finalTabs);
                }
            });
        });
    }

    public void loadWanArticles(int authorId, int page, int size, SwipeRefreshLayout swipeRefreshLayout) {
        ThreadManager.getInstance().runIo(() -> {
            IWanRepo repo = new WanRepo();
            List<WanArticleEntity> datas = repo.getWanArticleLocalList(authorId, page * size);
            if (datas == null) {
                datas = repo.getWanArticleNetList(authorId, page);
            }
            List<WanArticleEntity> finalDatas = datas;
            ThreadManager.getInstance().runOnUiThread(() -> {
                if (finalDatas != null && finalDatas.size() > 0) {
                    mWanArticleLiveData.setValue(finalDatas);
                }
                swipeRefreshLayout.setRefreshing(false);
            });
        });
    }

    public void onRefreshArticles(int authorId,
                                  int page,
                                  SwipeRefreshLayout swipeRefreshLayout) {
        ThreadManager.getInstance().runIo(() -> {
            IWanRepo repo = new WanRepo();
            List<WanArticleEntity> datas = repo.getWanArticleNetList(authorId, page);
            ThreadManager.getInstance().runOnUiThread(() -> {
                if (datas != null && datas.size() > 0) {
                    List<WanArticleEntity> oldDatas = mWanArticleLiveData.getValue();
                    if (oldDatas == null) {
                        oldDatas = new ArrayList<>();
                    }
                    oldDatas.addAll(0, datas);
                    mWanArticleLiveData.setValue(oldDatas);
                }
                swipeRefreshLayout.setRefreshing(false);
            });
        });
    }
}
