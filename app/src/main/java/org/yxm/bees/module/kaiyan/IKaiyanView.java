package org.yxm.bees.module.kaiyan;

import org.yxm.bees.entity.kaiyan.KaiyanCategory;

import java.util.List;

/**
 * Created by yxm on 2018.7.29.
 */

public interface IKaiyanView {
    void initDataSuccess(List<KaiyanCategory> categories);

    void initDataFailed(Throwable t);
}
