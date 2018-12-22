package org.yxm.bees.module.wanandroid.tab;

import android.os.AsyncTask;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.model.IWanModel;
import org.yxm.bees.model.impl.WanModel;
import org.yxm.lib.async.ThreadManager;

import java.util.List;

public class WanTabPresenter extends BasePresenter<IWanTabView> {
    private IWanModel mWanModel;

    WanTabPresenter() {
        if (mWanModel == null) {
            mWanModel = new WanModel();
        }
    }

    public void startLoadData(int authorId) {
//        ThreadManager.getInstance().runIo(() -> {
//            List<WanArticleEntity> datas = mWanModel.syncGetWanArticleDatas(authorId);
//            ThreadManager.getInstance().runOnUiThread(() ->
//                    mView.get().onLoadDataFinish(datas));
//        });

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
                });

        new AsyncTask<String, Void, List<WanArticleEntity>>() {
            @Override
            protected List<WanArticleEntity> doInBackground(String... params) {
                return null;
            }

            @Override
            protected void onPostExecute(List<WanArticleEntity> wanArticleEntities) {
                super.onPostExecute(wanArticleEntities);
            }
        }.execute();
    }
}
