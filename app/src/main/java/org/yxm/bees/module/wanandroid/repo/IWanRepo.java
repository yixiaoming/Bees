package org.yxm.bees.module.wanandroid.repo;

import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.entity.wan.WanTabEntity;

import java.util.List;

public interface IWanRepo {

    public List<WanTabEntity> getWanTabLocalList();

    public List<WanTabEntity> getWanTabNetList();

    public List<WanArticleEntity> getWanArticleLocalList(int tabId, int size);

    public List<WanArticleEntity> getWanArticleNetList(int tabId, int page);
}
