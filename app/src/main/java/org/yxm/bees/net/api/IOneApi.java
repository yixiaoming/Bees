package org.yxm.bees.net.api;


import org.yxm.bees.entity.oneapi.OneApiListEntity;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IOneApi {

    @GET("api/onelist/idlist")
    Call<OneApiListEntity> getOneApiList();

}
