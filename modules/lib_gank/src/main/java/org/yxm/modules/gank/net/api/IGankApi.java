package org.yxm.modules.gank.net.api;


import io.reactivex.Observable;
import java.util.List;
import org.yxm.modules.gank.entity.GankBaseEntity;
import org.yxm.modules.gank.entity.GankCategoryEntity;
import org.yxm.modules.gank.entity.GankEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IGankApi {

  public static final String BASE_URL = "http://gank.io/api/";

  @GET("xiandu/categories")
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

  @GET("random/data/{type}/{count}")
  Observable<GankBaseEntity<List<GankEntity>>> getRandomContentsRx(
      @Path("type") String type,
      @Path("count") int count
  );
}
