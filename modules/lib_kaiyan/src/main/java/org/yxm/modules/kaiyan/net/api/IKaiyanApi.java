package org.yxm.modules.kaiyan.net.api;

import io.reactivex.Observable;
import java.util.List;
import org.yxm.modules.kaiyan.entity.KaiyanCategory;
import org.yxm.modules.kaiyan.entity.KaiyanVideoList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by yxm on 2018.7.29.
 */

public interface IKaiyanApi {

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

  @GET("api/v4/categories/")
  Observable<List<KaiyanCategory>> getCategoriesRx();

  @GET("api/v3/categories/videoList")
  Observable<KaiyanVideoList> getVideoListRx(
      @Query("id") int id
  );

  @GET("api/v3/categories/videoList")
  Observable<KaiyanVideoList> getVideoListRx(
      @Query("id") int id,
      @Query("start") int start,
      @Query("num") int num
  );
}
