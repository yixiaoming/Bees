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
import org.yxm.bees.base.MvpFragment;
import org.yxm.bees.main.contract.NewsContract;
import org.yxm.bees.main.adapter.NewsPagerAdapter;
import org.yxm.bees.main.presenter.NewsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yixiaoming on 2018/6/9.
 */

public class NewsFragment extends MvpFragment<NewsPresenter>
        implements NewsContract.View, ViewPager.OnPageChangeListener {

    public static final String MAIN_FRAGMENT_ID = "main_fragment_id";

    private TabLayout mTablayout;
    private ViewPager mViewpager;

    private NewsPagerAdapter mViewpagerAdapter;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected NewsPresenter createPresenter() {
        return new NewsPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_layout, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.get().start();
    }

    private void initViews(View root) {
        mTablayout = root.findViewById(R.id.main_tablayout);
        mViewpager = root.findViewById(R.id.main_viewpager);
    }

    @Override
    public void initDataView(List<String> titles) {
        List<Fragment> fragments = new ArrayList<>();
        for (String title : titles) {
            fragments.add(TitleFragment.newInstance(title));
        }

        mViewpagerAdapter = new NewsPagerAdapter(getFragmentManager(), titles, fragments);
        mTablayout.setTabsFromPagerAdapter(mViewpagerAdapter);
        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTablayout.setupWithViewPager(mViewpager);

        mViewpager.setAdapter(mViewpagerAdapter);
        mViewpager.setOffscreenPageLimit(5);
        mViewpager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
