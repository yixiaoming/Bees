package org.yxm.lib.base.components;

import android.arch.lifecycle.ViewModel;

public final class PresenterViewModel<V extends BaseContact.View, P extends BaseContact.Presenter<V>>
    extends ViewModel {

  private P mPresenter;

  void setPresenter(P presenter) {
    if (mPresenter == null) {
      mPresenter = presenter;
    }
  }

  P getPresenter() {
    return mPresenter;
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    if (mPresenter != null) {
      mPresenter.onPresenterDestroy();
      mPresenter = null;
    }
  }
}
