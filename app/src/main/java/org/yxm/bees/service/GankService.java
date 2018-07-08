package org.yxm.bees.service;


import org.yxm.bees.entity.gankio.GankBaseEntity;
import org.yxm.bees.entity.gankio.GankCategoryEntity;
import org.yxm.bees.entity.gankio.GankEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankService {

    @GET("xiandu/categories ")
    Call<GankBaseEntity<List<GankCategoryEntity>>> getCategores();

    @GET("data/{type}/{count}/{page}")
    Call<GankBaseEntity<List<GankEntity>>> getContents(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page
    );

    @GET("random/data/{type}/{count}")
    Call<GankBaseEntity<List<GankEntity>>> getRandomContents(
            @Path("type") String type,
            @Path("count") int count
    );
}
