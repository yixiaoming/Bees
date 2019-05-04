package org.yxm.modules.base.mvp;

import java.lang.ref.WeakReference;

/**
 * Created by yixiaoming on 2018/6/8.
 */

public abstract class BasePresenter<V> {

  protected WeakReference<V> mView;

  public void attachView(V view) {
    mView = new WeakReference<V>(view);
  }

  public void detachView() {
    mView.clear();
  }
}
