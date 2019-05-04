package org.yxm.modules.ting;

import org.yxm.modules.base.mvp.BasePresenter;
import org.yxm.modules.ting.entity.ting.SongBillListEntity;
import org.yxm.modules.ting.model.ITingModel;
import org.yxm.modules.ting.model.impl.TingModel;

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
