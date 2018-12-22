package org.yxm.bees.module.wanandroid;

import org.yxm.bees.entity.wan.WanTabEntity;

import java.util.List;

/**
 * WanFragment UI层接口
 */
public interface IWanView {

    void onInitTabLayout(List<WanTabEntity> tabs);
}
