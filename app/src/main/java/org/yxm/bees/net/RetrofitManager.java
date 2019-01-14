package org.yxm.bees.net;

import org.yxm.bees.net.api.IGankApi;
import org.yxm.bees.net.api.IKaiyanApi;
import org.yxm.bees.net.api.ITingApi;
import org.yxm.bees.module.wanandroid.repo.network.IWanApi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    public static final String UA_KEY = "User-Agent";
    public static final String UA_VALUE = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)";

    private static volatile RetrofitManager instance;
    private OkHttpClient mOkHttpClient;
    private Retrofit mGankRetrofit;
    private Retrofit mKaiyanRetrofit;
    private Retrofit mTingRetrofit;
    private Retrofit mWanRetrofit;

    private IGankApi mGankApi;
    private IKaiyanApi mKaiyanApi;
    private ITingApi mTingApi;
    private IWanApi mIWanApi;

    private RetrofitManager() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request newRequest = chain.request().newBuilder()
                                .removeHeader(UA_KEY)
                                .addHeader(UA_KEY, UA_VALUE)
                                .build();
                        return chain.proceed(newRequest);
                    })
                    .build();
        }
        if (mGankRetrofit == null) {
            mGankRetrofit = new Retrofit.Builder()
                    .baseUrl(IGankApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
            mGankApi = mGankRetrofit.create(IGankApi.class);
        }
        if (mKaiyanRetrofit == null) {
            mKaiyanRetrofit = new Retrofit.Builder()
                    .baseUrl(IKaiyanApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
            mKaiyanApi = mKaiyanRetrofit.create(IKaiyanApi.class);
        }
        if (mTingRetrofit == null) {
            mTingRetrofit = new Retrofit.Builder()
                    .baseUrl(ITingApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
            mTingApi = mTingRetrofit.create(ITingApi.class);
        }
        if (mWanRetrofit == null) {
            mWanRetrofit = new Retrofit.Builder()
                    .baseUrl(IWanApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
            mIWanApi = mWanRetrofit.create(IWanApi.class);
        }
    }

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    public IGankApi getGankApi() {
        return mGankApi;
    }

    public IKaiyanApi getKaiyanApi() {
        return mKaiyanApi;
    }

    public ITingApi getThingApi() {
        return mTingApi;
    }

    public IWanApi getWanApi() {
        return mIWanApi;
    }

}
