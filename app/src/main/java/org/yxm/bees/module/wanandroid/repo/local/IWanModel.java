package org.yxm.bees.module.wanandroid.repo.local;

import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.entity.wan.WanTabEntity;

import java.util.List;

/**
 * Wan 的model接口，控制网络，数据库，缓存数据
 */
public interface IWanModel {

    public interface LoadDataListener<T> {

        void onSuccess(int code, T datas);

        void onFialed(int code, Throwable throwable);
    }

    List<WanTabEntity> getWanTabs();

    List<WanArticleEntity> syncGetWanArticleDatas(int authorId);

    void asyncGetWanArticleDatas(int authorId,
                                 LoadDataListener<List<WanArticleEntity>> loadDataListener,
                                 int page);
}
