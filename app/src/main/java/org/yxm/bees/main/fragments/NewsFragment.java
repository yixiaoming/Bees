package org.yxm.bees.main.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.bees.main.adapter.NewsPagerAdapter;
import org.yxm.bees.main.presenter.NewsPresenter;
import org.yxm.bees.main.view.INewsView;
import org.yxm.bees.pojo.TabInfo;
import org.yxm.bees.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yixiaoming on 2018/6/9.
 */

public class NewsFragment extends BaseMvpFragment<INewsView, NewsPresenter>
        implements INewsView {

    private static final String TAG = "NewsFragment";

    public static final String MAIN_FRAGMENT_ID = "main_fragment_id";

    private TabLayout mTablayout;
    private ViewPager mViewpager;

    private NewsPagerAdapter mViewpagerAdapter;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected NewsPresenter createPresenter() {
        return new NewsPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtil.e(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.main_fragment_layout, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.e(TAG, "onViewCreated");
        mPresenter.loadData();
    }

    private void initViews(View root) {
        mTablayout = root.findViewById(R.id.main_tablayout);
        mViewpager = root.findViewById(R.id.main_viewpager);
    }

    @Override
    public void initDataView(List<TabInfo> tabInfos) {
        LogUtil.e(TAG, "initDataView:");
        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();
        for (TabInfo tabinfo : tabInfos) {
            titles.add(tabinfo.name);
            fragments.add(NewsPageItemFragment.newInstance(tabinfo));
        }
        // getChildFragmentManager()：fragment下面的子fragment，child的fragmentmanager
        mViewpagerAdapter = new NewsPagerAdapter(getChildFragmentManager(), titles, fragments);
        mTablayout.setTabsFromPagerAdapter(mViewpagerAdapter);
        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTablayout.setupWithViewPager(mViewpager);

        mViewpager.setAdapter(mViewpagerAdapter);
        mViewpager.setOffscreenPageLimit(5);
    }
}
