package org.yxm.modules.kaiyan.tab;

import java.util.List;
import org.yxm.modules.kaiyan.entity.KaiyanVideoItem;

/**
 * Created by yxm on 2018.7.29.
 */

public interface IKaiyanTabView {

  void onInitLocalDataSuccess(List<KaiyanVideoItem> datas);

  void onInitLocalDataFailed(Throwable t);

  void onInitNetDataSuccess(List<KaiyanVideoItem> datas);

  void onInitNetDataFailed(Throwable t);

  void onRefreshSuccess(List<KaiyanVideoItem> datas);

  void onRefreshFailed(Throwable t);

  void onLoadmoreSuccess(List<KaiyanVideoItem> datas);

  void onLoadmoreFailed(Throwable t);
}
