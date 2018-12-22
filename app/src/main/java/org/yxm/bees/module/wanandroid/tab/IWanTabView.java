package org.yxm.bees.module.wanandroid.tab;

import org.yxm.bees.entity.wan.WanArticleEntity;

import java.util.List;

public interface IWanTabView {

    void onLoadDataFinish(List<WanArticleEntity> datas);
}
