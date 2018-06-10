package org.yxm.bees.main.contract;

import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.base.BaseView;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/8.
 */

public interface NewsContract {

    interface Model {

        void initTitles(OnLoadTitlesListener listener);

        interface OnLoadTitlesListener {
            void onSuccess(List<String> datas);

            void onFailed(int status);
        }

    }


    interface View extends BaseView<Presenter> {
        void initDataView(List<String> titles);
    }

    interface Presenter extends BasePresenter {
        void start();
    }
}
