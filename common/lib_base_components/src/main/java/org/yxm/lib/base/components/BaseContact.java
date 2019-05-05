package org.yxm.lib.base.components;

import android.arch.lifecycle.Lifecycle;

/**
 * Google官方示例Contact将View和Presetner关联
 */
public interface BaseContact {

  interface View {

  }

  interface Presenter<V extends BaseContact.View> {

    void attachLifecycle(Lifecycle lifecycle);

    void detatchLifecycle(Lifecycle lifecycle);

    void attachView(V view);

    void detatchView(V view);

    V getView();

    boolean isViewAttached();

    /**
     * 当presenter被销毁时需要做的清理工作ww
     */
    void onPresenterDestroy();
  }

}
