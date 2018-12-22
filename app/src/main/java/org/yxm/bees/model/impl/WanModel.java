package org.yxm.bees.model.impl;

import org.yxm.bees.entity.wan.WanBaseEntity;
import org.yxm.bees.entity.wan.WanTabEntity;
import org.yxm.bees.model.IWanModel;
import org.yxm.bees.net.RetrofitManager;
import org.yxm.bees.net.api.IWanApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * WanModel的具体实现
 */
public class WanModel implements IWanModel {

    @Override
    public List<WanTabEntity> getWanTabs() {
        IWanApi wanApi = RetrofitManager.getInstance().getWanApi();
        Call<WanBaseEntity<List<WanTabEntity>>> call = wanApi.listWanTabs();

        try {
            Response<WanBaseEntity<List<WanTabEntity>>> response = call.execute();
            if (response.isSuccessful() && response.body() != null
                    && response.body().errorCode == WanBaseEntity.ERRCODE_SUCCESS) {
                return response.body().data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
