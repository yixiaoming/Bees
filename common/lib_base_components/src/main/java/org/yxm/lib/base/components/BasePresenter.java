package org.yxm.lib.base.components;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;

public abstract class BasePresenter<V extends BaseContact.View>
    implements LifecycleObserver, BaseContact.Presenter<V> {

  private V mView;

  @Override
  final public V getView() {
    return mView;
  }

  @Override
  final public void attachLifecycle(Lifecycle lifecycle) {
    lifecycle.addObserver(this);
  }

  @Override
  final public void detatchLifecycle(Lifecycle lifecycle) {
    lifecycle.removeObserver(this);
  }

  @Override
  final public void attachView(V view) {
    mView = view;
  }

  @Override
  final public void detatchView(V view) {
    mView = null;
  }

  @Override
  final public boolean isViewAttached() {
    return mView != null;
  }
}
