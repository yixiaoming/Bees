package org.yxm.bees.module.wanandroid.repo;

import org.yxm.bees.db.AppDatabase;
import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.entity.wan.WanBaseEntity;
import org.yxm.bees.entity.wan.WanPageEntity;
import org.yxm.bees.entity.wan.WanTabEntity;
import org.yxm.bees.module.wanandroid.repo.local.IWanDao;
import org.yxm.bees.module.wanandroid.repo.network.IWanApi;
import org.yxm.bees.net.RetrofitManager;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Wan的数据仓库
 * 封装网络请求，本地数据
 */
public class WanRepo implements IWanRepo {

    private IWanDao mWanDao;
    private IWanApi mWanApi;

    public WanRepo() {
        mWanDao = AppDatabase.getInstance().getWanDao();
        mWanApi = RetrofitManager.getInstance().getWanApi();
    }

    /**
     * 获取Wan本地数据
     * @return
     */
    public List<WanTabEntity> getWanTabLocalList() {
        return mWanDao.getWantabs();
    }

    /**
     * 获取Wan网络数据
     * @return
     */
    public List<WanTabEntity> getWanTabNetList() {
        Call<WanBaseEntity<List<WanTabEntity>>> call = mWanApi.listWanTabs();
        try {
            Response<WanBaseEntity<List<WanTabEntity>>> response = call.execute();
            if (response.isSuccessful() && response.body() != null
                    && response.body().errorCode == WanBaseEntity.ERRCODE_SUCCESS) {
                mWanDao.insertWanTabs(response.body().data);
                return response.body().data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * 获取本地Wan文章list
     * @return
     */
    public List<WanArticleEntity> getWanArticleLocalList(int chapterId, int size) {
        return mWanDao.getWanArticles(chapterId, size);
    }


    /**
     * 获取网络Wan文章list
     * @return
     */
    public List<WanArticleEntity> getWanArticleNetList(int chapterId, int page) {
        Call<WanBaseEntity<WanPageEntity<WanArticleEntity>>> call =
                mWanApi.listAuthorArticles(chapterId, page);
        try {
            Response<WanBaseEntity<WanPageEntity<WanArticleEntity>>> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                mWanDao.insertWanArticles(response.body().data.datas);
                return response.body().data.datas;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
