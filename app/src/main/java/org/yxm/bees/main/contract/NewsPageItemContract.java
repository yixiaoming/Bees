package org.yxm.bees.main.contract;

import org.yxm.bees.base.BaseView;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/10.
 */

public interface NewsPageItemContract {

    interface Model {

        void initDatas(OnLoadTitleDataListener listener);

        interface OnLoadTitleDataListener {
            void onSuccess(List<String> datas);

            void onFailed(int statu);
        }
    }

    interface View extends BaseView<Presenter> {

        void initDatas(List<String> datas);

    }

    interface Presenter {
        void start();
    }
}
