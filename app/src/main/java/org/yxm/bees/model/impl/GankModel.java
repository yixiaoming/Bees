package org.yxm.bees.model.impl;

import android.annotation.SuppressLint;

import org.yxm.bees.db.AppDatabase;
import org.yxm.bees.db.dao.GankDao;
import org.yxm.entity.gankio.GankBaseEntity;
import org.yxm.entity.gankio.GankEntity;
import org.yxm.entity.gankio.GankTabEntity;
import org.yxm.bees.model.IGankModel;
import org.yxm.bees.net.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GankModel implements IGankModel {

    private static final String TAG = "GankModel";

    public static final String[] DEFAULT_TYPES = {
//            "all",
            "Android", "iOS", "福利", "拓展资源", "前端", "瞎推荐", "App"
    };

    public static final String[] DEFAULT_NAMES = {
//            "推荐",
            "Android", "IOS", "福利", "拓展", "前端", "瞎推荐", "App"
    };

    private static final int DEFAULT_PAGESIZE = 15;

    public List<GankTabEntity> getDefaultTabs() {
        List<GankTabEntity> tabs = new ArrayList<>();
        for (int i = 0; i < DEFAULT_TYPES.length; i++) {
            tabs.add(new GankTabEntity(DEFAULT_TYPES[i], DEFAULT_NAMES[i]));
        }
        return tabs;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadLocalData(String type, LoadDataListener listener) {

        Observable.create((ObservableOnSubscribe<List<GankEntity>>) emitter -> {
            GankDao dao = AppDatabase.getInstance().getGankDao();
            List<GankEntity> datas = dao.getLastDatas(type);
            emitter.onNext(datas);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gankEntities -> listener.onSuccess(gankEntities));
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadNetData(String type, LoadDataListener listener) {

        Observable<GankBaseEntity<List<GankEntity>>> call = RetrofitManager.getInstance()
                .getGankApi()
                .getRandomContentsRx(type, DEFAULT_PAGESIZE);
        call.subscribeOn(Schedulers.io())
                .doOnNext(listGankBaseEntity -> {
                    GankDao gankDao = AppDatabase.getInstance().getGankDao();
                    gankDao.insertAll(listGankBaseEntity.results);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listGankBaseEntity -> listener.onSuccess(listGankBaseEntity.results),
                        throwable -> listener.onFailed(throwable));
    }
}
