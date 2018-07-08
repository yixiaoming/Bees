package org.yxm.bees.model;

import android.util.Log;

import org.yxm.bees.entity.toutiao.ToutiaoTab;
import org.yxm.bees.service.ToutiaoService;
import org.yxm.bees.util.TimeUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ToutiaoModel {

    public static final String[] DEFAULT_TABNAME = {
            "推荐", "热点", "视频", "社会", "娱乐", "科技", "问答", "汽车", "财经", "军事"
    };
    private static final String TAG = "ToutiaoModel";

    public List<ToutiaoTab> getTabs() {
        List<ToutiaoTab> tabs = new ArrayList<>();
        for (String tab : DEFAULT_TABNAME) {
            tabs.add(new ToutiaoTab(tab));
        }
        return tabs;
    }

    public void getTabList(String category) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ToutiaoService.HOST)
                .build();

        ToutiaoService service = retrofit.create(ToutiaoService.class);
        Call<ResponseBody> call = service.getNewsArticle1(
                category, TimeUtil.getCurrentTimeInt()
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onResponse: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }
}
