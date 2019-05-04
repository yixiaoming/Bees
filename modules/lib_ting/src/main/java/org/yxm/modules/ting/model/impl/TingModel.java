package org.yxm.modules.ting.model.impl;

import org.yxm.modules.ting.entity.ting.SongBillListEntity;
import org.yxm.modules.ting.model.ITingModel;
import org.yxm.modules.ting.net.TingNetManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TingModel implements ITingModel {

  @Override
  public void getSongBillListNetData(int type, int size, int offset,
      final ILoadDataLisener<SongBillListEntity> listener) {
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
