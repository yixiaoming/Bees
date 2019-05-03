package org.yxm.modules.gank.tab;

import java.util.List;
import org.yxm.modules.gank.entity.GankEntity;

public interface IGankTabView {

    void onInitDataSuccess(List<GankEntity> datas);

    void onInitDataFailed(Throwable e);

    void onRefreshSuccess(List<GankEntity> datas);

    void onRefreshFailed(Throwable e);

    void onLoadMoreSuccess(List<GankEntity> datas);

    void onLoadMoreFailed(Throwable e);

    void showToast(String msg);
}
