package org.yxm.modules.wan.tab;

import java.util.List;
import org.yxm.modules.wan.entity.WanArticleEntity;

public interface IWanTabView {

  void onLoadDataFinish(List<WanArticleEntity> datas);
}
