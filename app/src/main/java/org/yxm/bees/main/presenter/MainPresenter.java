package org.yxm.bees.main.presenter;

import android.util.Log;

import org.yxm.bees.main.contract.MainContract;
import org.yxm.bees.main.repository.MainRepository;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/8.
 */

public class MainPresenter implements MainContract.Presenter {

    public static final String TAG = "MainPresenter";

    private MainContract.View mView;
    private MainContract.Repository mRepository;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
        this.mRepository = new MainRepository();
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mRepository.initTitles(new MainContract.Repository.OnLoadTitlesListener() {
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
