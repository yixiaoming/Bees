package org.yxm.bees.model;


import android.os.Handler;
import android.os.Looper;

import org.yxm.bees.db.AppDatabase;
import org.yxm.bees.db.dao.KaiyanDao;
import org.yxm.bees.entity.kaiyan.KaiyanCategory;
import org.yxm.bees.entity.kaiyan.KaiyanVideoItem;
import org.yxm.bees.entity.kaiyan.KaiyanVideoList;
import org.yxm.bees.net.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanModel implements IKaiyanModel {

    public static final String TAG = "KaiyanModel";

    private static final int DEFAULT_PAGE_SIZE = 10;

    private int mStart = DEFAULT_PAGE_SIZE;

    @Override
    public void loadLocalCatetories(LoadDataListener listener) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            KaiyanDao dao = AppDatabase.getInstance().getKaiyanDao();
            List<KaiyanCategory> list = dao.getCategoryes();
            if (list != null && list.size() > 0) {
                handler.post(() -> listener.onSuccess(list));
            } else {
                handler.post(() -> listener.onFailed(new RuntimeException("local data empty")));
            }
        }).start();
    }

    @Override
    public void loadNetCategories(LoadDataListener listener) {
        Call<List<KaiyanCategory>> call = RetrofitManager.getInstance()
                .getKaiyanApi().getCategories();
        call.enqueue(new Callback<List<KaiyanCategory>>() {
            @Override
            public void onResponse(Call<List<KaiyanCategory>> call,
                                   Response<List<KaiyanCategory>> response) {
                List<KaiyanCategory> list = response.body();
                if (list != null && list.size() > 0) {
                    listener.onSuccess(list);
                    new Thread(() -> {
                        AppDatabase.getInstance().getKaiyanDao().insertCatetories(list);
                    }).start();
                } else {
                    listener.onFailed(new RuntimeException("net data empty"));
                }

            }

            @Override
            public void onFailure(Call<List<KaiyanCategory>> call, Throwable t) {
                listener.onFailed(t);
            }
        });
    }

    @Override
    public void loadNextPageVideos(int tabId, LoadDataListener listener) {
        Call<KaiyanVideoList> call = RetrofitManager.getInstance()
                .getKaiyanApi()
                .getVideoList(tabId, mStart, DEFAULT_PAGE_SIZE);
        call.enqueue(new Callback<KaiyanVideoList>() {
            @Override
            public void onResponse(Call<KaiyanVideoList> call, Response<KaiyanVideoList> response) {
                KaiyanVideoList list = response.body();
                List<KaiyanVideoItem> videoItems = new ArrayList<>();
                for (KaiyanVideoItem item : list.itemList) {
                    if (item.data == null || item.data.author == null || item.data.cover == null
                            || item.data.playUrl == null || item.data.webUrl == null) {
                        continue;
                    }
                    item.tabId = tabId;
                    videoItems.add(item);
                }
                if (videoItems.size() > 0) {
                    listener.onSuccess(videoItems);
                    new Thread(() -> {
                        KaiyanDao dao = AppDatabase.getInstance().getKaiyanDao();
                        dao.insertVideos(videoItems);
                    }).start();
                    mStart += DEFAULT_PAGE_SIZE;
                } else {
                    listener.onFailed(new RuntimeException("load video net data failed"));
                }
            }

            @Override
            public void onFailure(Call<KaiyanVideoList> call, Throwable t) {
                listener.onFailed(t);
            }
        });
    }

    @Override
    public void loadLocalVideos(int tabid, LoadDataListener listener) {
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            KaiyanDao dao = AppDatabase.getInstance().getKaiyanDao();
            List<KaiyanVideoItem> list = dao.getVideos(tabid);
            if (list != null && list.size() > 0) {
                handler.post(() -> listener.onSuccess(list));
            } else {
                handler.post(() -> listener.onFailed(new RuntimeException("local data empty")));
            }
        }).start();
    }

    @Override
    public void loadNetVideos(int tabid, LoadDataListener listener) {
        Call<KaiyanVideoList> call = RetrofitManager.getInstance()
                .getKaiyanApi()
                .getVideoList(tabid);
        call.enqueue(new Callback<KaiyanVideoList>() {
            @Override
            public void onResponse(Call<KaiyanVideoList> call, Response<KaiyanVideoList> response) {
                KaiyanVideoList list = response.body();
                List<KaiyanVideoItem> videoItems = new ArrayList<>();
                for (KaiyanVideoItem item : list.itemList) {
                    if (item.data == null || item.data.author == null || item.data.cover == null
                            || item.data.playUrl == null || item.data.webUrl == null) {
                        continue;
                    }
                    item.tabId = tabid;
                    videoItems.add(item);
                }
                if (videoItems.size() > 0) {
                    listener.onSuccess(videoItems);
                    new Thread(() -> {
                        KaiyanDao dao = AppDatabase.getInstance().getKaiyanDao();
                        dao.insertVideos(videoItems);
                    }).start();
                } else {
                    listener.onFailed(new RuntimeException("load video net data failed"));
                }
            }

            @Override
            public void onFailure(Call<KaiyanVideoList> call, Throwable t) {
                listener.onFailed(t);
            }
        });
    }


}
