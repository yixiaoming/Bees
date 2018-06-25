package org.yxm.bees.main.model;

import org.yxm.bees.entity.TabInfo;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/11.
 */

public interface INewsModel {

    void initTitles(OnLoadTitlesListener listener);

    interface OnLoadTitlesListener {
        void onSuccess(List<TabInfo> datas);

        void onFailed(int status);
    }
}
