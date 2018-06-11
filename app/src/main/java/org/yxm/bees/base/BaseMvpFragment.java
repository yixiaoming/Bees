package org.yxm.bees.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by yixiaoming on 2018/4/6.
 */

public abstract class BaseMvpFragment<V, T extends BasePresenter<V>> extends Fragment {

    protected T mPresenter;

    protected abstract T createPresenter();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }
}
