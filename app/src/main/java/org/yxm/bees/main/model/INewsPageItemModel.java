package org.yxm.bees.main.model;

import org.yxm.bees.pojo.Blog;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/11.
 */

public interface INewsPageItemModel {

    void initDatas(OnLoadTitleDataListener listener);

    interface OnLoadTitleDataListener {
        void onSuccess(List<Blog> datas);

        void onFailed(int status);
    }
}
