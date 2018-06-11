package org.yxm.bees.main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yixiaoming on 2018/6/10.
 */

public class NewsPageItemModel implements INewsPageItemModel {

    @Override
    public void initDatas(OnLoadTitleDataListener listener) {
        List<String> datas = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            datas.add("content:" + i);
        }
        listener.onSuccess(datas);
    }
}
