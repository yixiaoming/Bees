package org.yxm.bees.main;

/**
 * Created by yixiaoming on 2018/6/8.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private MainContract.Repository mRepository;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
        this.mRepository = new MainRepository();
        mView.setPresenter(this);
    }


    @Override
    public void start() {

    }
}
