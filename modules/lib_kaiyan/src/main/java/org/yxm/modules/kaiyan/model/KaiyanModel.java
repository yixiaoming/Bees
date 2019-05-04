package org.yxm.modules.kaiyan.model;


import android.annotation.SuppressLint;
import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import org.yxm.modules.kaiyan.dao.KaiyanDao;
import org.yxm.modules.kaiyan.db.AppDatabase;
import org.yxm.modules.kaiyan.entity.KaiyanCategory;
import org.yxm.modules.kaiyan.entity.KaiyanVideoItem;
import org.yxm.modules.kaiyan.entity.KaiyanVideoList;
import org.yxm.modules.kaiyan.net.RetrofitManager;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanModel implements IKaiyanModel {

  public static final String TAG = "KaiyanModel";

  private static final int DEFAULT_PAGE_SIZE = 10;

  private int mStart = DEFAULT_PAGE_SIZE;

  private Context mAppContext;

  public KaiyanModel(Context context) {
    this.mAppContext = context;
  }

  @SuppressLint("CheckResult")
  @Override
  public void loadLocalCatetories(final LoadDataListener listener) {
    Observable.create(new ObservableOnSubscribe<List<KaiyanCategory>>() {
      @Override
      public void subscribe(ObservableEmitter<List<KaiyanCategory>> emitter)
          throws Exception {
        KaiyanDao dao = AppDatabase.getInstance(mAppContext).getKaiyanDao();
        List<KaiyanCategory> list = dao.getCategoryes();
        emitter.onNext(list);
      }
    })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<KaiyanCategory>>() {
          @Override
          public void accept(List<KaiyanCategory> list) throws Exception {
            if (list != null && list.size() > 0) {
              listener.onSuccess(list);
            } else {
              listener.onFailed(new RuntimeException("catetories is empty"));
            }
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            listener.onFailed(throwable);
          }
        });
  }

  @SuppressLint("CheckResult")
  @Override
  public void loadNetCategories(final LoadDataListener listener) {
    Observable<List<KaiyanCategory>> call = RetrofitManager.getInstance()
        .getKaiyanApi()
        .getCategoriesRx();
    call.subscribeOn(Schedulers.io())
        .doOnNext(new Consumer<List<KaiyanCategory>>() {
          @Override
          public void accept(List<KaiyanCategory> kaiyanCategories) throws Exception {
            if (kaiyanCategories != null) {
              AppDatabase.getInstance(mAppContext).getKaiyanDao()
                  .insertCatetories(kaiyanCategories);
            }
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<KaiyanCategory>>() {
          @Override
          public void accept(List<KaiyanCategory> kaiyanCategories) throws Exception {
            listener.onSuccess(kaiyanCategories);
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            listener.onFailed(new RuntimeException("net data empty"));
          }
        });
  }

  @SuppressLint("CheckResult")
  @Override
  public void loadNextPageVideos(final int tabId, final LoadDataListener listener) {
    Observable<KaiyanVideoList> call = RetrofitManager.getInstance()
        .getKaiyanApi()
        .getVideoListRx(tabId, mStart, DEFAULT_PAGE_SIZE);
    call.subscribeOn(Schedulers.io())
        .map(new Function<KaiyanVideoList, List<KaiyanVideoItem>>() {
          @Override
          public List<KaiyanVideoItem> apply(KaiyanVideoList list) throws Exception {
            List<KaiyanVideoItem> videoItems = new ArrayList<>();
            for (
                KaiyanVideoItem item : list.itemList) {
              if (item.data == null || item.data.author == null || item.data.cover == null
                  || item.data.playUrl == null || item.data.webUrl == null) {
                continue;
              }
              item.tabId = tabId;
              videoItems.add(item);
            }
            return videoItems;
          }
        })
        .doOnNext(new Consumer<List<KaiyanVideoItem>>() {
          @Override
          public void accept(List<KaiyanVideoItem> kaiyanVideoItems) throws Exception {
            KaiyanDao dao = AppDatabase.getInstance(mAppContext).getKaiyanDao();
            dao.insertVideos(kaiyanVideoItems);
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<KaiyanVideoItem>>() {
          @Override
          public void accept(List<KaiyanVideoItem> list) throws Exception {
            listener.onSuccess(list);
            mStart += DEFAULT_PAGE_SIZE;
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            listener.onFailed(throwable);
          }
        });
  }

  @SuppressLint("CheckResult")
  @Override
  public void loadLocalVideos(final int tabid, final LoadDataListener listener) {
    Observable.create(
        new ObservableOnSubscribe<List<KaiyanVideoItem>>() {
          @Override
          public void subscribe(ObservableEmitter<List<KaiyanVideoItem>> emitter)
              throws Exception {
            KaiyanDao dao = AppDatabase.getInstance(mAppContext).getKaiyanDao();
            List<KaiyanVideoItem> list = dao.getVideos(tabid);
            emitter.onNext(list);
          }
        }
    )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<KaiyanVideoItem>>() {
          @Override
          public void accept(List<KaiyanVideoItem> list) throws Exception {
            if (list != null && list.size() > 0) {
              listener.onSuccess(list);
            } else {
              listener.onFailed(new RuntimeException("videos is empty"));
            }
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            listener.onFailed(throwable);
          }
        });
  }

  @SuppressLint("CheckResult")
  @Override
  public void loadNetVideos(final int tabId, final LoadDataListener listener) {
    Observable<KaiyanVideoList> call = RetrofitManager.getInstance()
        .getKaiyanApi()
        .getVideoListRx(tabId);
    call
        .subscribeOn(Schedulers.io())
        .map(new Function<KaiyanVideoList, List<KaiyanVideoItem>>() {
          @Override
          public List<KaiyanVideoItem> apply(KaiyanVideoList list) throws Exception {
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
          }
        })
        .doOnNext(new Consumer<List<KaiyanVideoItem>>() {
          @Override
          public void accept(List<KaiyanVideoItem> kaiyanVideoItems) throws Exception {
            KaiyanDao dao = AppDatabase.getInstance(mAppContext).getKaiyanDao();
            dao.insertVideos(kaiyanVideoItems);
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<KaiyanVideoItem>>() {
          @Override
          public void accept(List<KaiyanVideoItem> list) throws Exception {
            listener.onSuccess(list);
            mStart += DEFAULT_PAGE_SIZE;
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
            listener.onFailed(throwable);
          }
        });
  }
}
