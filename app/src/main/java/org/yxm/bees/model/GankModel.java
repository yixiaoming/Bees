package org.yxm.bees.model;

import android.support.annotation.NonNull;

import org.yxm.bees.entity.gankio.GankBaseEntity;
import org.yxm.bees.entity.gankio.GankCategoryEntity;
import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.entity.gankio.GankTabEntity;
import org.yxm.bees.exception.DataEmptyException;
import org.yxm.bees.service.GankService;

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
            "all", "Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App"
    };

    public static final String[] DEFAULT_NAMES = {
            "推荐", "Android", "IOS", "休息视频", "福利", "拓展", "前端", "瞎推荐", "App"
    };

    private static final int DEFAULT_PAGESIZE = 20;

    public List<GankTabEntity> getDefaultTabs() {
        List<GankTabEntity> tabs = new ArrayList<>();
        for (int i = 0; i < DEFAULT_TYPES.length; i++) {
            tabs.add(new GankTabEntity(DEFAULT_TYPES[i], DEFAULT_NAMES[i]));
        }
        return tabs;
    }

    @Override
    public void getCategories(@NonNull LoadCategoryListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GANKIO_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<GankBaseEntity<List<GankCategoryEntity>>> call = retrofit
                .create(GankService.class)
                .getCategores();

        call.enqueue(new Callback<GankBaseEntity<List<GankCategoryEntity>>>() {
            @Override
            public void onResponse(Call<GankBaseEntity<List<GankCategoryEntity>>> call,
                                   Response<GankBaseEntity<List<GankCategoryEntity>>> response) {
                if (response.body() == null) {
                    onFailure(call, new DataEmptyException());
                } else {
                    listener.onSuccess(response.body().results);
                }
            }

            @Override
            public void onFailure(Call<GankBaseEntity<List<GankCategoryEntity>>> call,
                                  Throwable t) {
                listener.onFailed();
            }
        });
    }

    @Override
    public void loadTabContent(String type, LoadTabContentListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GANKIO_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<GankBaseEntity<List<GankEntity>>> call = retrofit
                .create(GankService.class)
                .getRandomContents(type, DEFAULT_PAGESIZE);

        call.enqueue(new Callback<GankBaseEntity<List<GankEntity>>>() {
            @Override
            public void onResponse(Call<GankBaseEntity<List<GankEntity>>> call,
                                   Response<GankBaseEntity<List<GankEntity>>> response) {
                if (response.body() != null) {
                    listener.onSuccess(response.body().results);
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
