package org.yxm.lib.base.components;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<V extends BaseContact.View, P extends BaseContact.Presenter<V>>
    extends AppCompatActivity
    implements BaseContact.View {

  protected P mPresenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
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
  protected void onDestroy() {
    super.onDestroy();
    mPresenter.detatchLifecycle(getLifecycle());
    mPresenter.detatchView((V) this);
  }

  protected abstract P initPresenter();
}
