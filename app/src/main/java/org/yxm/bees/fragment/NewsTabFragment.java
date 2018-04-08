package org.yxm.bees.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseFragment;
import org.yxm.bees.util.T;

/**
 * Created by yixiaoming on 2018/4/6.
 */

public class NewsTabFragment extends BaseFragment {

    private static NewsTabFragment instance = null;
    private ViewPager mViewPager;
    private TabLayout mTablayout;
    private ImageView mAddChannelView;
    private LinearLayout mHeaderLayout;

    @Deprecated
    public NewsTabFragment() {
    }

    // 不要使用构造方法，使用单例获取
    public static NewsTabFragment getInstance() {
        if (instance == null) {
            synchronized (NewsTabFragment.class) {
                if (instance == null) {
                    instance = new NewsTabFragment();
                }
            }
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_news_fragment, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化view
     * @param view
     */
    private void initView(View view) {
        mTablayout = view.findViewById(R.id.news_tablayout);
        mViewPager = view.findViewById(R.id.news_view_pager);
        mTablayout.setupWithViewPager(mViewPager);
        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mAddChannelView = view.findViewById(R.id.add_channel_iv);
        mAddChannelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.s(getContext(), "add channel");
            }
        });

        mHeaderLayout = view.findViewById(R.id.header_layout);
    }

    /**
     * 初始化数据
     */
    private void initData(){

    }

    /**
     * 获取fragment tab，作为唯一标示传递个fragment manager
     * @return
     */
    public static String getFragmentTag() {
        return NewsTabFragment.class.getName();
    }

}
