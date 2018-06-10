package org.yxm.bees.main.repository;

import org.yxm.bees.main.contract.TitleContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yixiaoming on 2018/6/10.
 */

public class TitleModel implements TitleContract.Model {

    @Override
    public void initDatas(OnLoadTitleDataListener listener) {
        List<String> datas = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            datas.add("content:" + i);
        }
        listener.onSuccess(datas);
    }
}
