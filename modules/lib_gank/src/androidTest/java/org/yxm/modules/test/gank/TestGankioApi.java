package org.yxm.modules.test.gank;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yxm.modules.gank.entity.GankBaseEntity;
import org.yxm.modules.gank.entity.GankCategoryEntity;
import org.yxm.modules.gank.entity.GankEntity;
import org.yxm.modules.gank.net.api.IGankApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(AndroidJUnit4.class)
public class TestGankioApi {

  private static final String TAG = "TestGankioApi";

  public static final String DOMAIN = "http://gank.io/api/";


  @Test
  public void testGetCategory() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    Call<GankBaseEntity<List<GankCategoryEntity>>> call = retrofit
        .create(IGankApi.class)
        .getCategores();

    call.enqueue(new Callback<GankBaseEntity<List<GankCategoryEntity>>>() {
      @Override
      public void onResponse(Call<GankBaseEntity<List<GankCategoryEntity>>> call,
          Response<GankBaseEntity<List<GankCategoryEntity>>> response) {
        Log.d(TAG, "onResponse: " + response.body().results);
      }

      @Override
      public void onFailure(Call<GankBaseEntity<List<GankCategoryEntity>>> call,
          Throwable t) {
        Log.d(TAG, "onFailure: " + t);
      }
    });
  }

  @Test
  public void testGetContent() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(DOMAIN)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    Call<GankBaseEntity<List<GankEntity>>> call = retrofit
        .create(IGankApi.class)
        .getRandomContents("all", 10);

    call.enqueue(new Callback<GankBaseEntity<List<GankEntity>>>() {
      @Override
      public void onResponse(Call<GankBaseEntity<List<GankEntity>>> call,
          Response<GankBaseEntity<List<GankEntity>>> response) {
        Log.d(TAG, "onResponse: " + response.body().results);
      }

      @Override
      public void onFailure(Call<GankBaseEntity<List<GankEntity>>> call,
          Throwable t) {
        Log.d(TAG, "onFailure: " + t);
      }
    });
  }

}
