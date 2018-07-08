package org.yxm.bees.service;


import org.yxm.bees.entity.oneapi.OneApiListEntity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OneApiService {

    @GET("api/onelist/idlist")
    Call<OneApiListEntity> getOneApiList();

}
