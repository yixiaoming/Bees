package org.yxm.modules.wan.repo;

import java.util.List;
import org.yxm.modules.wan.entity.WanArticleEntity;
import org.yxm.modules.wan.entity.WanTabEntity;

public interface IWanRepo {

    List<WanTabEntity> getWanTabLocalList();

    List<WanTabEntity> getWanTabNetList();

    List<WanArticleEntity> getWanArticleLocalList(int tabId, int size);

    List<WanArticleEntity> getWanArticleNetList(int tabId, int page);
}
