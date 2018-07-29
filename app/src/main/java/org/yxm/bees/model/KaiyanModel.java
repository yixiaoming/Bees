package org.yxm.bees.model;

import android.util.Log;

import org.yxm.bees.api.KaiyanApi;
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
    public void getCategories(LoadDataListener listener) {
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
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<KaiyanCategory>> call, Throwable t) {
                listener.onFailed(t);
            }
        });
    }

    @Override
    public void loadVideoData(int tabid, LoadDataListener listener) {
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
                listener.onSuccess(list.itemList);
            }

            @Override
            public void onFailure(Call<KaiyanVideoList> call, Throwable t) {
                listener.onFailed(t);
            }
        });
    }


}
