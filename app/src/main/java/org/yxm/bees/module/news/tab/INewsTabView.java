package org.yxm.bees.module.news.tab;

import org.yxm.bees.entity.gankio.GankEntity;

import java.util.List;

public interface INewsTabView {

    void initDatas(List<GankEntity> datas);

    void onRefreshDatas(List<GankEntity> datas);

    void onLoadMoreDatas(List<GankEntity> datas);

    void showToast(String msg);
}
