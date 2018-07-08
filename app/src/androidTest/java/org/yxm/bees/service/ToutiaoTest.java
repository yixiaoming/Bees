package org.yxm.bees.service;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.yxm.bees.util.TimeUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@RunWith(AndroidJUnit4.class)
public class ToutiaoTest {

    private static final String TAG = "ToutiaoTest";

    @Test
    public void testTetNewsArticle1() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ToutiaoService.HOST)
                .build();

        ToutiaoService service = retrofit.create(ToutiaoService.class);
        Call<ResponseBody> call = service.getNewsArticle1(
                "推荐", TimeUtil.getCurrentTimeInt()
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: " + response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });

    }


}
