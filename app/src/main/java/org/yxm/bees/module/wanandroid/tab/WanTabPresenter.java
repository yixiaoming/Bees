package org.yxm.bees.module.wanandroid.tab;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.module.wanandroid.repo.local.IWanModel;
import org.yxm.bees.module.wanandroid.repo.local.WanModel;
import org.yxm.lib.async.ThreadManager;

import java.util.List;

public class WanTabPresenter extends BasePresenter<IWanTabView> {
    private IWanModel mWanModel;

    WanTabPresenter() {
        if (mWanModel == null) {
            mWanModel = new WanModel();
        }
    }

    public void startLoadData(int authorId, int page) {
        mWanModel.asyncGetWanArticleDatas(authorId,
                new IWanModel.LoadDataListener<List<WanArticleEntity>>() {
                    @Override
                    public void onSuccess(int code, List<WanArticleEntity> datas) {
                        ThreadManager.getInstance().runOnUiThread(() -> {
                            mView.get().onLoadDataFinish(datas);
                        });
                    }

                    @Override
                    public void onFialed(int code, Throwable throwable) {

                    }
                }, page);
    }
}
