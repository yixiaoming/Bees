package org.yxm.bees.module.gank.tab;

import org.yxm.entity.gankio.GankEntity;

import java.util.List;

public interface IGankTabView {

    void onInitDataSuccess(List<GankEntity> datas);

    void onInitDataFailed(Throwable e);

    void onRefreshSuccess(List<GankEntity> datas);

    void onRefreshFailed(Throwable e);

    void onLoadMoreSuccess(List<GankEntity> datas);

    void onLoadMoreFailed(Throwable e);

    void showToast(String msg);
}
