package org.yxm.bees.model;

import org.yxm.entity.ting.SongBillListEntity;

public interface ITingModel {

    void getSongBillListNetData(int type, int size, int offset, ILoadDataLisener<SongBillListEntity> listener);

    interface ILoadDataLisener<T> {
        void onSuccess(T t);

        void onFailed(Throwable t);
    }
}
