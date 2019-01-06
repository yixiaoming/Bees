package org.yxm.bees.module.wanandroid;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;
import android.os.Looper;

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

    public void loadWanArticles(int authorId) {
        ThreadManager.getInstance().runIo(() -> {
            IWanModel mModel = new WanModel();
            List<WanArticleEntity> datas = mModel.syncGetWanArticleDatas(authorId);
            ThreadManager.getInstance().runOnUiThread(() -> {
                if (datas != null) {
                    mWanArticleLiveData.setValue(datas);
                }
            });
        });
    }
}
