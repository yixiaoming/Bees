package org.yxm.bees.main.presenter;

import android.util.Log;

import org.yxm.bees.main.contract.NewsContract;
import org.yxm.bees.main.repository.NewsModel;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/8.
 */

public class NewsPresenter implements NewsContract.Presenter {

    public static final String TAG = "NewsPresenter";

    private NewsContract.View mView;
    private NewsContract.Model mRepository;

    public NewsPresenter(NewsContract.View mView) {
        this.mView = mView;
        this.mRepository = new NewsModel();
    }

    @Override
    public void start() {
        mRepository.initTitles(new NewsContract.Model.OnLoadTitlesListener() {
            @Override
            public void onSuccess(List<String> datas) {
                mView.initDataView(datas);
            }

            @Override
            public void onFailed(int status) {
                Log.d(TAG, "onFailed: " + status);
            }
        });
    }
}
