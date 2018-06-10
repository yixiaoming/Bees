package org.yxm.bees.main.presenter;

import org.yxm.bees.main.contract.NewsPageItemContract;
import org.yxm.bees.main.repository.NewsPageItemModel;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/10.
 */

public class NewsPageItemPresenter implements NewsPageItemContract.Presenter {

    private NewsPageItemContract.Model mModel;
    private NewsPageItemContract.View mView;

    public NewsPageItemPresenter(NewsPageItemContract.View mView) {
        this.mView = mView;
        this.mModel = new NewsPageItemModel();
    }


    @Override
    public void start() {
        mModel.initDatas(new NewsPageItemContract.Model.OnLoadTitleDataListener() {
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
