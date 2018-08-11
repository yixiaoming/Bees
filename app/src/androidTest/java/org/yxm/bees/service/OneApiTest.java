package org.yxm.bees.service;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.yxm.bees.net.api.OneApi;
import org.yxm.entity.oneapi.OneApiListEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(AndroidJUnit4.class)
public class OneApiTest {

    private static final String TAG = "OneApiTest";
    public static final String DOMAIN = "http://v3.wufazhuce.com:8000/";

    @Test
    public void testGetOneApiList() {
        Gson gson = new GsonBuilder()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        OneApi service = retrofit.create(OneApi.class);
        Call<OneApiListEntity> call = service.getOneApiList();
        call.enqueue(new Callback<OneApiListEntity>() {
            @Override
            public void onResponse(Call<OneApiListEntity> call, Response<OneApiListEntity> response) {
                Log.d(TAG, "onResponse: " + response.body());

            }

            @Override
            public void onFailure(Call<OneApiListEntity> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
