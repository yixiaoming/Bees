package org.yxm.modules.oneapi;

import org.yxm.lib.base.components.BasePresenter;


class OneApiPresenter extends BasePresenter<OneApiView> {

  public void initData() {
    getView().onInitData();
  }

  @Override
  public void onPresenterDestroy() {

  }
}
