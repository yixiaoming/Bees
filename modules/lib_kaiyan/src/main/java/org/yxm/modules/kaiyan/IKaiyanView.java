package org.yxm.modules.kaiyan;

import java.util.List;
import org.yxm.modules.kaiyan.entity.KaiyanCategory;

/**
 * Created by yxm on 2018.7.29.
 */

public interface IKaiyanView {
    void initLocalDataSuccess(List<KaiyanCategory> categories);

    void initLocalDataFailed(Throwable t);

    void initNetDataFailed(Throwable t);
}
