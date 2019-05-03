package org.yxm.modules.wan;

import java.util.List;
import org.yxm.modules.wan.entity.WanTabEntity;

/**
 * WanFragment UI层接口
 */
public interface IWanView {

    void onInitTabLayout(List<WanTabEntity> tabs);
}
