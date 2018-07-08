package org.yxm.bees.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ToutiaoService {

    String HOST = "http://toutiao.com/";

    @GET("api/article/recent/?source=2&as=A105177907376A5&cp=5797C7865AD54E1&count=30")
    Call<ResponseBody> getNewsArticle1(
            @Query("category") String category,
            @Query("_") int max_behot_time);
}
