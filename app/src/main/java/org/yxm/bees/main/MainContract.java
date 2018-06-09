package org.yxm.bees.main;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.base.BaseView;

/**
 * Created by yixiaoming on 2018/6/8.
 */

public interface MainContract {

    interface Repository {

    }

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void start();
    }
}
