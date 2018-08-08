package org.yxm.bees.model.impl;

import android.annotation.SuppressLint;
import android.media.MediaSync;
import android.util.Log;

import org.yxm.bees.db.AppDatabase;
import org.yxm.bees.db.dao.GankDao;
import org.yxm.bees.entity.gankio.GankBaseEntity;
import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.entity.gankio.GankTabEntity;
import org.yxm.bees.model.IGankModel;
import org.yxm.bees.net.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

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

        Observable.create(new ObservableOnSubscribe<List<GankEntity>>() {
            @Override
            public void subscribe(ObservableEmitter<List<GankEntity>> emitter) throws Exception {
                GankDao dao = AppDatabase.getInstance().getGankDao();
                List<GankEntity> datas = dao.getLastDatas(type);
                emitter.onNext(datas);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GankEntity>>() {
                    @Override
                    public void accept(List<GankEntity> gankEntities) throws Exception {
                        listener.onSuccess(gankEntities);
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadNetData(String type, LoadDataListener listener) {

        Observable<GankBaseEntity<List<GankEntity>>> call = RetrofitManager.getInstance()
                .getGankApi()
                .getRandomContentsRx(type, DEFAULT_PAGESIZE);
        call.subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<GankBaseEntity<List<GankEntity>>>() {
                    @Override
                    public void accept(GankBaseEntity<List<GankEntity>> listGankBaseEntity) throws Exception {
                        GankDao gankDao = AppDatabase.getInstance().getGankDao();
                        gankDao.insertAll(listGankBaseEntity.results);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankBaseEntity<List<GankEntity>>>() {
                    @Override
                    public void accept(GankBaseEntity<List<GankEntity>> listGankBaseEntity) throws Exception {
                        listener.onSuccess(listGankBaseEntity.results);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        listener.onFailed(throwable);
                    }
                });
    }
}
