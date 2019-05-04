package org.yxm.bees.service;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yxm.bees.entity.kaiyan.KaiyanCategory;
import org.yxm.bees.entity.kaiyan.KaiyanVideoItem;
import org.yxm.bees.entity.kaiyan.KaiyanVideoList;
import org.yxm.bees.net.api.IKaiyanApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yxm on 2018.7.29.
 */
@RunWith(AndroidJUnit4.class)
public class TestKaiyanApi {

  public static final String TAG = "TestKaiyanApi";
  public static final String BASE_URL = "http://baobab.kaiyanapp.com/";

  public void testGetCategories() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    Call<List<KaiyanCategory>> call = retrofit
        .create(IKaiyanApi.class)
        .getCategories();
    call.enqueue(new Callback<List<KaiyanCategory>>() {
      @Override
      public void onResponse(Call<List<KaiyanCategory>> call,
          Response<List<KaiyanCategory>> response) {
        List<KaiyanCategory> list = response.body();
        for (KaiyanCategory category : list) {
          Log.d(TAG, "onResponse: " + category);
        }
      }

      @Override
      public void onFailure(Call<List<KaiyanCategory>> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t);
      }
    });
  }

  @Test
  public void testGetVideoList() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    Call<KaiyanVideoList> call = retrofit
        .create(IKaiyanApi.class)
        .getVideoList(18);
    call.enqueue(new Callback<KaiyanVideoList>() {
      @Override
      public void onResponse(Call<KaiyanVideoList> call, Response<KaiyanVideoList> response) {
        KaiyanVideoList list = response.body();
        Log.d(TAG, "onResponse: " + list.nextPageUrl);
        List<KaiyanVideoItem> items = list.itemList;
        for (KaiyanVideoItem item : items) {
          Log.d(TAG, "onResponse: " + item);
        }
      }

      @Override
      public void onFailure(Call<KaiyanVideoList> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t);
      }
    });
  }
}
