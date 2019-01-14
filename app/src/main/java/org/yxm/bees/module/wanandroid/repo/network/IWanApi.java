package org.yxm.bees.module.wanandroid.repo.network;

import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.entity.wan.WanArticleWithTag;
import org.yxm.bees.entity.wan.WanBaseEntity;
import org.yxm.bees.entity.wan.WanPageEntity;
import org.yxm.bees.entity.wan.WanTabEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Wan 的所有网络请求api接口
 */
public interface IWanApi {

    String BASE_URL = "http://wanandroid.com";

    @GET(value = "/wxarticle/chapters/json")
    Call<WanBaseEntity<List<WanTabEntity>>> listWanTabs();

    @GET(value = "/wxarticle/list/{authorId}/{page}/json")
    Call<WanBaseEntity<WanPageEntity<WanArticleEntity>>> listAuthorArticles(
            @Path("authorId") int authorId,
            @Path("page") int page
    );

    @GET(value = "/wxarticle/list/{authorId}/{page}/json")
    Call<WanBaseEntity<WanPageEntity<WanArticleWithTag>>> listAuthorArticleWithTag(
            @Path("authorId") int authorId,
            @Path("page") int page
    );

}
