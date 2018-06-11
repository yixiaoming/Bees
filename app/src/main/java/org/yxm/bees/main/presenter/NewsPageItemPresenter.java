package org.yxm.bees.main.presenter;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.main.model.INewsPageItemModel;
import org.yxm.bees.main.model.NewsPageItemModel;
import org.yxm.bees.main.view.INewsPageItemView;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/10.
 */

public class NewsPageItemPresenter extends BasePresenter<INewsPageItemView> {

    private INewsPageItemModel mModel;

    public NewsPageItemPresenter() {
        this.mModel = new NewsPageItemModel();
    }

    public void start() {
        mModel.initDatas(new INewsPageItemModel.OnLoadTitleDataListener() {
            @Override
            public void onSuccess(List<String> datas) {
                if (mView.get() != null) {
                    mView.get().initDatas(datas);
                }
            }

            @Override
            public void onFailed(int statu) {

            }
        });
    }
}
