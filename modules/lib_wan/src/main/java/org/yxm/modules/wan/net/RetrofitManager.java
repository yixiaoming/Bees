package org.yxm.modules.wan.net;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.yxm.modules.wan.repo.network.IWanApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    public static final String UA_KEY = "User-Agent";
    public static final String UA_VALUE = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)";

    private static volatile RetrofitManager instance;
    private OkHttpClient mOkHttpClient;
    private Retrofit mKaiyanRetrofit;
    private Retrofit mTingRetrofit;
    private Retrofit mWanRetrofit;

    private IWanApi mIWanApi;

    private RetrofitManager() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                .removeHeader(UA_KEY)
                                .addHeader(UA_KEY, UA_VALUE)
                                .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();
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

    public IWanApi getWanApi() {
        return mIWanApi;
    }

}
