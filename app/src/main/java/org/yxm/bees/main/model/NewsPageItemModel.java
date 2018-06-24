package org.yxm.bees.main.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.yxm.bees.pojo.BaseEntity;
import org.yxm.bees.pojo.Blog;
import org.yxm.bees.service.BlogService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yixiaoming on 2018/6/10.
 */

public class NewsPageItemModel implements INewsPageItemModel {

    public static final String TAG = "NewsPageItemModel";

    public static final String DOMAIN = "http://192.168.1.101:4567/";

    @Override
    public void initDatas(final OnLoadTitleDataListener listener) {
        Log.d(TAG, "initDatas: ");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DOMAIN)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BlogService service = retrofit.create(BlogService.class);
        Call<BaseEntity<List<Blog>>> call = service.getBlogs();
        call.enqueue(new Callback<BaseEntity<List<Blog>>>() {
            @Override
            public void onResponse(Call<BaseEntity<List<Blog>>> call, Response<BaseEntity<List<Blog>>> response) {
                BaseEntity<List<Blog>> baseEntity = response.body();
                listener.onSuccess(baseEntity.data);
            }

            @Override
            public void onFailure(Call<BaseEntity<List<Blog>>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }
}
