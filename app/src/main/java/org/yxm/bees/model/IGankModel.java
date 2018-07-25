package org.yxm.bees.model;

import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.entity.gankio.GankTabEntity;

import java.util.List;

public interface IGankModel {

    List<GankTabEntity> getDefaultTabs();

    void loadTabContent(String type, LoadTabContentListener listener);

    interface LoadTabContentListener {
        void onSuccess(List<GankEntity> contents);

        void onFailed(Exception e);
    }
}
