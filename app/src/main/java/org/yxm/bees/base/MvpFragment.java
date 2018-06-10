package org.yxm.bees.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by yixiaoming on 2018/4/6.
 */

public abstract class MvpFragment<T> extends Fragment {

    protected WeakReference<T> mPresenter;

    protected abstract T createPresenter();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = new WeakReference<T>(createPresenter());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.clear();
    }

}
