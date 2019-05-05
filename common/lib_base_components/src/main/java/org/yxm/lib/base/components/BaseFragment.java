package org.yxm.lib.base.components;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class BaseFragment<V extends BaseContact.View, P extends BaseContact.Presenter<V>>
    extends Fragment
    implements BaseContact.View {

  protected P mPresenter;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    PresenterViewModel<V, P> viewModel =
        ViewModelProviders.of(this).get(PresenterViewModel.class);
    if (viewModel.getPresenter() == null) {
      viewModel.setPresenter(initPresenter());
    }
    mPresenter = viewModel.getPresenter();
    mPresenter.attachLifecycle(getLifecycle());
    mPresenter.attachView((V) this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mPresenter.detatchLifecycle(getLifecycle());
    mPresenter.detatchView((V) this);
  }

  protected abstract P initPresenter();

}
