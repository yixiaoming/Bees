package org.yxm.bees.main.presenter;

import android.util.Log;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.main.model.INewsModel;
import org.yxm.bees.main.model.NewsModel;
import org.yxm.bees.main.view.INewsView;
import org.yxm.bees.pojo.TabInfo;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/8.
 */

public class NewsPresenter extends BasePresenter<INewsView> {

    public static final String TAG = "NewsPresenter";

    private INewsModel mModel;

    public NewsPresenter() {
        this.mModel = new NewsModel();
    }

    public void loadData() {
        mModel.initTitles(new INewsModel.OnLoadTitlesListener() {
            @Override
            public void onSuccess(List<TabInfo> datas) {
                if (mView.get() != null) {
                    mView.get().initDataView(datas);
                }
            }

            @Override
            public void onFailed(int status) {
                Log.d(TAG, "onFailed: " + status);
            }
        });
    }
}
