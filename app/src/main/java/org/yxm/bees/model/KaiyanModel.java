package org.yxm.bees.model;


import android.os.Handler;
import android.os.Looper;

import org.yxm.bees.api.KaiyanApi;
import org.yxm.bees.db.AppDatabase;
import org.yxm.bees.db.dao.KaiyanDao;
import org.yxm.bees.entity.kaiyan.KaiyanCategory;
import org.yxm.bees.entity.kaiyan.KaiyanVideoItem;
import org.yxm.bees.entity.kaiyan.KaiyanVideoList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanModel implements IKaiyanModel {

    public static final String TAG = "KaiyanModel";
    public static final String BASE_URL = "http://baobab.kaiyanapp.com/";

    private String mNextPageUrl = "";

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<List<KaiyanCategory>> call = retrofit
                .create(KaiyanApi.class)
                .getCategories();
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<KaiyanVideoList> call = retrofit
                .create(KaiyanApi.class)
                .getVideoList(tabid);
        call.enqueue(new Callback<KaiyanVideoList>() {
            @Override
            public void onResponse(Call<KaiyanVideoList> call, Response<KaiyanVideoList> response) {
                KaiyanVideoList list = response.body();
                mNextPageUrl = list.nextPageUrl;
                List<KaiyanVideoItem> videoItems = list.itemList;
                for (KaiyanVideoItem item : videoItems) {
                    item.tabId = tabid;
                }
                if (videoItems != null && videoItems.size() > 0) {
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
