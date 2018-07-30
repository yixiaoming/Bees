package org.yxm.bees.module.gank.tab;

import org.yxm.bees.entity.gankio.GankEntity;

import java.util.List;

public interface IGankTabView {

    void initDatas(List<GankEntity> datas);

    void initDatasFailed(Exception e);

    void onRefreshSuccess(List<GankEntity> datas);

    void onRefreshFailed(Exception e);

    void onLoadMoreSuccess(List<GankEntity> datas);

    void onLoadMoreFailed(Exception e);

    void showToast(String msg);
}