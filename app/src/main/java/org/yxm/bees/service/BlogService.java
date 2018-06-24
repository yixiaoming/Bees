package org.yxm.bees.service;

import org.yxm.bees.pojo.BaseEntity;
import org.yxm.bees.pojo.Blog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BlogService {

    @GET("blog")
    Call<BaseEntity<List<Blog>>> getBlogs();

    @GET("blog/{id}")
    Call<BaseEntity<Blog>> getBlog(@Path("id") int id);
}
