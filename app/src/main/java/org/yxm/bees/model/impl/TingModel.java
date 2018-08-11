package org.yxm.bees.model.impl;

import org.yxm.entity.ting.SongBillListEntity;
import org.yxm.bees.model.ITingModel;
import org.yxm.bees.net.TingNetManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TingModel implements ITingModel {
    @Override
    public void getSongBillListNetData(int type, int size, int offset, ILoadDataLisener<SongBillListEntity> listener) {
        TingNetManager.getSongBillListData(type, size, offset, new Callback<SongBillListEntity>() {
            @Override
            public void onResponse(Call<SongBillListEntity> call, Response<SongBillListEntity> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<SongBillListEntity> call, Throwable t) {
                listener.onFailed(t);
            }
        });
    }
}
