package org.yxm.modules.wan;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;
import java.util.ArrayList;
import java.util.List;
import org.yxm.components.runtime.AppRuntime;
import org.yxm.lib.async.ThreadManager;
import org.yxm.modules.wan.entity.WanArticleEntity;
import org.yxm.modules.wan.entity.WanTabEntity;
import org.yxm.modules.wan.repo.IWanRepo;
import org.yxm.modules.wan.repo.WanRepo;

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
    ThreadManager.getInstance().runIo(new Runnable() {
      @Override
      public void run() {
        IWanRepo repo = new WanRepo(AppRuntime.getContext());
        List<WanTabEntity> tabs = repo.getWanTabLocalList();
        if (tabs == null || tabs.size() == 0) {
          tabs = repo.getWanTabNetList();
        }
        final List<WanTabEntity> finalTabs = tabs;
        ThreadManager.getInstance().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if (finalTabs != null) {
              mWanTabLiveData.setValue(finalTabs);
            }
          }
        });
      }
    });
  }

  public void loadWanArticles(final int authorId, final int page, final int size,
      final SwipeRefreshLayout swipeRefreshLayout) {
    ThreadManager.getInstance().runIo(new Runnable() {
      @Override
      public void run() {
        IWanRepo repo = new WanRepo(AppRuntime.getContext());
        List<WanArticleEntity> datas = repo.getWanArticleLocalList(authorId, page * size);
        if (datas == null) {
          datas = repo.getWanArticleNetList(authorId, page);
        }
        final List<WanArticleEntity> finalDatas = datas;
        ThreadManager.getInstance().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if (finalDatas != null && finalDatas.size() > 0) {
              mWanArticleLiveData.setValue(finalDatas);
            }
            swipeRefreshLayout.setRefreshing(false);
          }
        });
      }
    });
  }

  public void onRefreshArticles(final int authorId,
      final int page,
      final SwipeRefreshLayout swipeRefreshLayout) {
    ThreadManager.getInstance().runIo(new Runnable() {
      @Override
      public void run() {
        IWanRepo repo = new WanRepo(AppRuntime.getContext());
        final List<WanArticleEntity> datas = repo.getWanArticleNetList(authorId, page);
        ThreadManager.getInstance().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if (datas != null && datas.size() > 0) {
              List<WanArticleEntity> oldDatas = mWanArticleLiveData.getValue();
              if (oldDatas == null) {
                oldDatas = new ArrayList<>();
              }
              oldDatas.addAll(0, datas);
              mWanArticleLiveData.setValue(oldDatas);
            }
            swipeRefreshLayout.setRefreshing(false);
          }
        });
      }
    });
  }
}
