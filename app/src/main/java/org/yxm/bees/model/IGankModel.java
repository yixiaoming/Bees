package org.yxm.bees.model;

import org.yxm.bees.entity.gankio.GankCategoryEntity;
import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.entity.gankio.GankTabEntity;

import java.util.List;

public interface IGankModel {

    List<GankTabEntity> getDefaultTabs();

    void getCategories(LoadCategoryListener listener);

    interface LoadCategoryListener {
        void onSuccess(List<GankCategoryEntity> catetories);

        void onFailed();
    }

    void loadTabContent(String type, LoadTabContentListener listener);

    interface LoadTabContentListener {
        void onSuccess(List<GankEntity> contents);

        void onFailed(Exception e);
    }

//    void refreshTabContent(String type, OnRefreshListener listener);
//
//    interface OnRefreshListener {
//        void onSuccess(List<GankEntity> contents);
//    }

}
