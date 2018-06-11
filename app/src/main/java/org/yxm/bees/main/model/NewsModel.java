package org.yxm.bees.main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yixiaoming on 2018/6/8.
 */

public class NewsModel implements INewsModel {

    @Override
    public void initTitles(OnLoadTitlesListener listener) {
        List<String> titles = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            titles.add("title:" + i);
        }
        listener.onSuccess(titles);
    }
}
