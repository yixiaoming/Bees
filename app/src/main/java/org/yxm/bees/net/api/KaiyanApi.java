package org.yxm.bees.net.api;

import org.yxm.bees.entity.kaiyan.KaiyanCategory;
import org.yxm.bees.entity.kaiyan.KaiyanVideoList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yxm on 2018.7.29.
 */

public interface KaiyanApi {

    public static final String BASE_URL = "http://baobab.kaiyanapp.com/";

    @GET("api/v4/categories/")
    Call<List<KaiyanCategory>> getCategories();

    @GET("api/v3/categories/videoList")
    Call<KaiyanVideoList> getVideoList(
            @Query("id") int id
    );

    @GET("api/v3/categories/videoList")
    Call<KaiyanVideoList> getVideoList(
            @Query("id") int id,
            @Query("start") int start,
            @Query("num") int num
    );
}
