package org.yxm.bees.module.kaiyan.tab;

import org.yxm.bees.entity.kaiyan.KaiyanVideoItem;

import java.util.List;

/**
 * Created by yxm on 2018.7.29.
 */

public interface IKaiyanTabView {
    void initLocalDataSuccess(List<KaiyanVideoItem> data);

    void initLocalDataFailed(Throwable t);

    void initNetDataFailed(Throwable t);
}