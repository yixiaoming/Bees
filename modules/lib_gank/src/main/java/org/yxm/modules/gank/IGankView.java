package org.yxm.modules.gank;

import java.util.List;
import org.yxm.modules.gank.entity.GankTabEntity;

public interface IGankView {

  void initDataView(List<GankTabEntity> tabInfos);
}
