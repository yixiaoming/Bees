package org.yxm.bees.module.news;

import org.yxm.bees.entity.gankio.GankTabEntity;

import java.util.List;

public interface INewsView {

    void initDataView(List<GankTabEntity> tabInfos);
}
