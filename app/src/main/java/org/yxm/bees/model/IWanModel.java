package org.yxm.bees.model;

import org.yxm.bees.entity.wan.WanTabEntity;

import java.util.List;

/**
 * Wan 的model接口，控制网络，数据库，缓存数据
 */
public interface IWanModel {
    List<WanTabEntity> getWanTabs();
}
