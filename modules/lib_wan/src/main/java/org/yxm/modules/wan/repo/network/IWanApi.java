package org.yxm.modules.wan.repo.network;

import java.util.List;
import org.yxm.modules.wan.entity.WanArticleEntity;
import org.yxm.modules.wan.entity.WanArticleWithTag;
import org.yxm.modules.wan.entity.WanBaseEntity;
import org.yxm.modules.wan.entity.WanPageEntity;
import org.yxm.modules.wan.entity.WanTabEntity;
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
