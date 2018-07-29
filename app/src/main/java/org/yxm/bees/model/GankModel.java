package org.yxm.bees.model;

import android.os.Handler;

import org.yxm.bees.api.GankApi;
import org.yxm.bees.db.AppDatabase;
import org.yxm.bees.entity.gankio.GankBaseEntity;
import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.entity.gankio.GankTabEntity;
import org.yxm.bees.db.dao.GankDao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GankModel implements IGankModel {

    private static final String TAG = "GankModel";

    private static final String GANKIO_DOMAIN = "http://gank.io/api/";

    public static final String[] DEFAULT_TYPES = {
//            "all",
            "Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App"
    };

    public static final String[] DEFAULT_NAMES = {
//            "推荐",
            "Android", "IOS", "休息视频", "福利", "拓展", "前端", "瞎推荐", "App"
    };

    private static final int DEFAULT_PAGESIZE = 15;

    public List<GankTabEntity> getDefaultTabs() {
        List<GankTabEntity> tabs = new ArrayList<>();
        for (int i = 0; i < DEFAULT_TYPES.length; i++) {
            tabs.add(new GankTabEntity(DEFAULT_TYPES[i], DEFAULT_NAMES[i]));
        }
        return tabs;
    }

    @Override
    public void loadLocalData(String type, LoadDataListener listener) {
        Handler handler = new Handler();
        new Thread(() -> {
            GankDao dao = AppDatabase.getInstance().gankDao();
            List<GankEntity> datas = dao.getLastDatas(type);
            handler.post(() -> listener.onSuccess(datas));
        }).start();
    }

    @Override
    public void loadNetData(String type, LoadDataListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GANKIO_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<GankBaseEntity<List<GankEntity>>> call = retrofit
                .create(GankApi.class)
                .getRandomContents(type, DEFAULT_PAGESIZE);

        call.enqueue(new Callback<GankBaseEntity<List<GankEntity>>>() {
            @Override
            public void onResponse(Call<GankBaseEntity<List<GankEntity>>> call,
                                   Response<GankBaseEntity<List<GankEntity>>> response) {
                if (response.body() != null) {
                    List<GankEntity> results = response.body().results;
                    listener.onSuccess(results);
                    new Thread(() -> {
                        GankDao gankDao = AppDatabase.getInstance().gankDao();
                        gankDao.insertAll(results);
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<GankBaseEntity<List<GankEntity>>> call,
                                  Throwable t) {
                listener.onFailed(new Exception(t));
            }
        });
    }
}
