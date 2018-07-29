package org.yxm.bees.model;

import android.util.Log;

import org.yxm.bees.api.KaiyanApi;
import org.yxm.bees.entity.kaiyan.KaiyanCategory;

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
}
