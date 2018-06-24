package org.yxm.bees.main.view;

import org.yxm.bees.pojo.Blog;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/11.
 */

public interface INewsPageItemView {
    void initDatas(List<Blog> datas);

    void onRefreshDatas(List<Blog> datas);

    void onLoadMoreDatas(List<Blog> datas);
}
