package org.yxm.bees.base;

import java.lang.ref.WeakReference;

/**
 * Created by yixiaoming on 2018/6/8.
 */

public abstract class BasePresenter<T> {

    protected WeakReference<T> mView;

    public void attachView(T view) {
        mView = new WeakReference<T>(view);
    }

    public void detachView() {
        mView.clear();
    }
}
