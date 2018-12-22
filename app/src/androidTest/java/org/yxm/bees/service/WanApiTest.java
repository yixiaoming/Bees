package org.yxm.bees.service;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.yxm.bees.db.AppDatabase;
import org.yxm.bees.db.dao.WanDao;
import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.entity.wan.WanArticleWithTag;
import org.yxm.bees.entity.wan.WanBaseEntity;
import org.yxm.bees.entity.wan.WanPageEntity;
import org.yxm.bees.entity.wan.WanTabEntity;
import org.yxm.bees.net.RetrofitManager;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RunWith(AndroidJUnit4.class)
public class WanApiTest {

    private static final String TAG = "WanApiTest";

    public void testWanGetTabs() {

        Call<WanBaseEntity<List<WanTabEntity>>> call =
                RetrofitManager.getInstance().getWanApi().listWanTabs();
        call.enqueue(new Callback<WanBaseEntity<List<WanTabEntity>>>() {
            @Override
            public void onResponse(Call<WanBaseEntity<List<WanTabEntity>>> call,
                                   Response<WanBaseEntity<List<WanTabEntity>>> response) {
                if (response.isSuccessful()) {
                    WanBaseEntity<List<WanTabEntity>> datas = response.body();

                    Log.d(TAG, "onResponse: " + datas);
                }
            }

            @Override
            public void onFailure(Call<WanBaseEntity<List<WanTabEntity>>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Test
    public void testWanGetArticles() {
        Call<WanBaseEntity<WanPageEntity<WanArticleEntity>>> call =
                RetrofitManager.getInstance().getWanApi().listAuthorArticles(409, 1);
        call.enqueue(new Callback<WanBaseEntity<WanPageEntity<WanArticleEntity>>>() {
            @Override
            public void onResponse(Call<WanBaseEntity<WanPageEntity<WanArticleEntity>>> call,
                                   Response<WanBaseEntity<WanPageEntity<WanArticleEntity>>> response) {
                if (response.isSuccessful()) {
                    for (WanArticleEntity entity : response.body().data.datas) {
                        Log.d(TAG, "onResponse: " + entity);

                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            WanDao wanDao = AppDatabase.getInstance().getWanDao();
                            for (WanArticleEntity entity : response.body().data.datas) {
                                if (null == wanDao.findWanArticleEntityById(entity.id)) {
                                    wanDao.insertWanArticle(entity);
                                }
                            }
                        }
                    }).start();

                }
            }

            @Override
            public void onFailure(Call<WanBaseEntity<WanPageEntity<WanArticleEntity>>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Test
    public void testWanGetArticleWithTag() {
        Call<WanBaseEntity<WanPageEntity<WanArticleWithTag>>> call =
                RetrofitManager.getInstance().getWanApi().listAuthorArticleWithTag(409, 1);
        call.enqueue(new Callback<WanBaseEntity<WanPageEntity<WanArticleWithTag>>>() {
            @Override
            public void onResponse(Call<WanBaseEntity<WanPageEntity<WanArticleWithTag>>> call,
                                   Response<WanBaseEntity<WanPageEntity<WanArticleWithTag>>> response) {
                if (response.isSuccessful()) {
                    for (WanArticleWithTag entity : response.body().data.datas) {
                        Log.d(TAG, "onResponse: " + entity);
                    }

                }
            }

            @Override
            public void onFailure(Call<WanBaseEntity<WanPageEntity<WanArticleWithTag>>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
