package org.yxm.bees.module.music;

import org.yxm.bees.entity.ting.SongBillListEntity;
import org.yxm.bees.model.ITingModel;
import org.yxm.bees.model.impl.TingModel;
import org.yxm.modules.base.BasePresenter;

public class TingPresenter extends BasePresenter<IMusicView> {

    private ITingModel mModel;
    private int mOffset = 0;

    public TingPresenter() {
        mModel = new TingModel();
    }

    public void doInitMusicList() {
        mModel.getSongBillListNetData(2, 10, mOffset, new ITingModel.ILoadDataLisener<SongBillListEntity>() {
            @Override
            public void onSuccess(SongBillListEntity songBillListEntity) {
                mOffset += mOffset;
                if (mView.get() != null) {
                    mView.get().onInitMusicListSuccess(songBillListEntity);
                }
            }

            @Override
            public void onFailed(Throwable t) {

            }
        });
    }


}
