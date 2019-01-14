package org.yxm.bees.module.wanandroid.repo.local;

import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.entity.wan.WanBaseEntity;
import org.yxm.bees.entity.wan.WanPageEntity;
import org.yxm.bees.entity.wan.WanTabEntity;
import org.yxm.bees.net.RetrofitManager;
import org.yxm.bees.module.wanandroid.repo.network.IWanApi;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * WanModel的具体实现
 */
public class WanModel implements IWanModel {

    @Override
    public List<WanTabEntity> getWanTabs() {
        IWanApi wanApi = RetrofitManager.getInstance().getWanApi();
        Call<WanBaseEntity<List<WanTabEntity>>> call = wanApi.listWanTabs();

        try {
            Response<WanBaseEntity<List<WanTabEntity>>> response = call.execute();
            if (response.isSuccessful() && response.body() != null
                    && response.body().errorCode == WanBaseEntity.ERRCODE_SUCCESS) {
                return response.body().data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<WanArticleEntity> syncGetWanArticleDatas(int authorId) {
        IWanApi wanApi = RetrofitManager.getInstance().getWanApi();
        Call<WanBaseEntity<WanPageEntity<WanArticleEntity>>> call = wanApi.listAuthorArticles(authorId, 0);
        try {
            Response<WanBaseEntity<WanPageEntity<WanArticleEntity>>> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().data.datas;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void asyncGetWanArticleDatas(int authorId,
                                        LoadDataListener<List<WanArticleEntity>> loadDataListener,
                                        int page) {
        IWanApi wanApi = RetrofitManager.getInstance().getWanApi();
        Call<WanBaseEntity<WanPageEntity<WanArticleEntity>>> call = wanApi.listAuthorArticles(authorId,
                page);
        call.enqueue(new Callback<WanBaseEntity<WanPageEntity<WanArticleEntity>>>() {
            @Override
            public void onResponse(Call<WanBaseEntity<WanPageEntity<WanArticleEntity>>> call,
                                   Response<WanBaseEntity<WanPageEntity<WanArticleEntity>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loadDataListener.onSuccess(0, response.body().data.datas);
                } else {
                    loadDataListener.onSuccess(-1, Collections.emptyList());
                }
            }

            @Override
            public void onFailure(Call<WanBaseEntity<WanPageEntity<WanArticleEntity>>> call, Throwable t) {
                loadDataListener.onFialed(0, t);
            }
        });
    }
}
