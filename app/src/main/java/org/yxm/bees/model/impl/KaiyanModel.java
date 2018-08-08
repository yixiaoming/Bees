package org.yxm.bees.model.impl;


import android.annotation.SuppressLint;

import org.yxm.bees.db.AppDatabase;
import org.yxm.bees.db.dao.KaiyanDao;
import org.yxm.bees.entity.kaiyan.KaiyanCategory;
import org.yxm.bees.entity.kaiyan.KaiyanVideoItem;
import org.yxm.bees.entity.kaiyan.KaiyanVideoList;
import org.yxm.bees.model.IKaiyanModel;
import org.yxm.bees.net.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanModel implements IKaiyanModel {

    public static final String TAG = "KaiyanModel";

    private static final int DEFAULT_PAGE_SIZE = 10;

    private int mStart = DEFAULT_PAGE_SIZE;

    @SuppressLint("CheckResult")
    @Override
    public void loadLocalCatetories(LoadDataListener listener) {
        Observable.create(
                (ObservableOnSubscribe<List<KaiyanCategory>>) emitter -> {
                    KaiyanDao dao = AppDatabase.getInstance().getKaiyanDao();
                    List<KaiyanCategory> list = dao.getCategoryes();
                    emitter.onNext(list);
                }
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            if (list != null && list.size() > 0) {
                                listener.onSuccess(list);
                            } else {
                                listener.onFailed(new RuntimeException("catetories is empty"));
                            }
                        },
                        throwable -> listener.onFailed(throwable)
                );
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadNetCategories(LoadDataListener listener) {
        Observable<List<KaiyanCategory>> call = RetrofitManager.getInstance()
                .getKaiyanApi()
                .getCategoriesRx();
        call.subscribeOn(Schedulers.io())
                .doOnNext(kaiyanCategories -> {
                    if (kaiyanCategories != null) {
                        AppDatabase.getInstance().getKaiyanDao().insertCatetories(kaiyanCategories);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        kaiyanCategories -> listener.onSuccess(kaiyanCategories),
                        throwable -> listener.onFailed(new RuntimeException("net data empty"))
                );
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadNextPageVideos(int tabId, LoadDataListener listener) {
        Observable<KaiyanVideoList> call = RetrofitManager.getInstance()
                .getKaiyanApi()
                .getVideoListRx(tabId, mStart, DEFAULT_PAGE_SIZE);
        call.subscribeOn(Schedulers.io())
                .map(list -> {
                    List<KaiyanVideoItem> videoItems = new ArrayList<>();
                    for (KaiyanVideoItem item : list.itemList) {
                        if (item.data == null || item.data.author == null || item.data.cover == null
                                || item.data.playUrl == null || item.data.webUrl == null) {
                            continue;
                        }
                        item.tabId = tabId;
                        videoItems.add(item);
                    }
                    return videoItems;
                })
                .doOnNext(kaiyanVideoItems -> {
                    KaiyanDao dao = AppDatabase.getInstance().getKaiyanDao();
                    dao.insertVideos(kaiyanVideoItems);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            listener.onSuccess(list);
                            mStart += DEFAULT_PAGE_SIZE;
                        },
                        throwable -> listener.onFailed(throwable)
                );
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadLocalVideos(int tabid, LoadDataListener listener) {
        Observable.create(
                (ObservableOnSubscribe<List<KaiyanVideoItem>>) emitter -> {
                    KaiyanDao dao = AppDatabase.getInstance().getKaiyanDao();
                    List<KaiyanVideoItem> list = dao.getVideos(tabid);
                    emitter.onNext(list);
                }
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                list -> {
                    if (list != null && list.size() > 0) {
                        listener.onSuccess(list);
                    } else {
                        listener.onFailed(new RuntimeException("videos is empty"));
                    }
                },
                throwable -> listener.onFailed(throwable)
        );
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadNetVideos(int tabId, LoadDataListener listener) {
        Observable<KaiyanVideoList> call = RetrofitManager.getInstance()
                .getKaiyanApi()
                .getVideoListRx(tabId);
        call
                .subscribeOn(Schedulers.io())
                .map(list -> {
                    List<KaiyanVideoItem> videoItems = new ArrayList<>();
                    for (KaiyanVideoItem item : list.itemList) {
                        if (item.data == null || item.data.author == null || item.data.cover == null
                                || item.data.playUrl == null || item.data.webUrl == null) {
                            continue;
                        }
                        item.tabId = tabId;
                        videoItems.add(item);
                    }
                    return videoItems;
                })
                .doOnNext(kaiyanVideoItems -> {
                    KaiyanDao dao = AppDatabase.getInstance().getKaiyanDao();
                    dao.insertVideos(kaiyanVideoItems);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> {
                            listener.onSuccess(list);
                            mStart += DEFAULT_PAGE_SIZE;
                        },
                        throwable -> listener.onFailed(throwable)
                );
    }


}
