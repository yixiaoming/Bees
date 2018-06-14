package org.yxm.bees.main.model;

import org.yxm.bees.pojo.TabInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yixiaoming on 2018/6/8.
 */

public class NewsModel implements INewsModel {

    @Override
    public void initTitles(OnLoadTitlesListener listener) {
        List<TabInfo> tabInfos = new ArrayList<>();
        tabInfos.add(new TabInfo("1", "娱乐", "www.baidu.com"));
        tabInfos.add(new TabInfo("2", "搞笑", "www.baidu.com"));
        tabInfos.add(new TabInfo("3", "微信推荐", "www.baidu.com"));
        tabInfos.add(new TabInfo("4", "今日头条", "www.baidu.com"));
        tabInfos.add(new TabInfo("5", "体育", "www.baidu.com"));
        tabInfos.add(new TabInfo("6", "买房", "www.baidu.com"));
        tabInfos.add(new TabInfo("7", "热门", "www.baidu.com"));
        tabInfos.add(new TabInfo("8", "二次元", "www.baidu.com"));
        tabInfos.add(new TabInfo("9", "旅游", "www.baidu.com"));
        tabInfos.add(new TabInfo("10", "小说", "www.baidu.com"));
        tabInfos.add(new TabInfo("11", "漫画", "www.baidu.com"));

        listener.onSuccess(tabInfos);
    }
}
