package org.yxm.bees.main.presenter;

import org.yxm.bees.main.contract.TitleContract;
import org.yxm.bees.main.repository.TitleModel;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/10.
 */

public class TitlePresenter implements TitleContract.Presenter {

    private TitleContract.Model mModel;
    private TitleContract.View mView;

    public TitlePresenter(TitleContract.View mView) {
        this.mView = mView;
        this.mModel = new TitleModel();
    }


    @Override
    public void start() {
        mModel.initDatas(new TitleContract.Model.OnLoadTitleDataListener() {
            @Override
            public void onSuccess(List<String> datas) {
                mView.initDatas(datas);
            }

            @Override
            public void onFailed(int statu) {

            }
        });
    }
}
