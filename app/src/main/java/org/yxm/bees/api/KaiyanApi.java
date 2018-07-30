package org.yxm.bees.api;

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

    @GET("api/v4/categories/")
    Call<List<KaiyanCategory>> getCategories();

    @GET("api/v4/categories/videoList")
    Call<KaiyanVideoList> getVideoList(
            @Query("id") int id
    );
}