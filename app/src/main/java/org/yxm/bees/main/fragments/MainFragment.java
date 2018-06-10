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
import org.yxm.bees.main.contract.MainContract;
import org.yxm.bees.main.adapter.MainViewPagerAdapter;
import org.yxm.bees.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yixiaoming on 2018/6/9.
 */

public class MainFragment extends Fragment
        implements MainContract.View, ViewPager.OnPageChangeListener {

    public static final String MAIN_FRAGMENT_ID = "main_fragment_id";

    private TabLayout mTablayout;
    private ViewPager mViewpager;

    private MainViewPagerAdapter mViewpagerAdapter;

    private MainContract.Presenter mPresenter;


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
        mPresenter.start();
    }

    private void initViews(View root) {
        mTablayout = root.findViewById(R.id.main_tablayout);
        mViewpager = root.findViewById(R.id.main_viewpager);
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initDataView(List<String> titles) {
        List<Fragment> fragments = new ArrayList<>();
        for (String title : titles) {
            fragments.add(TitleFragment.newInstance(title));
        }

        mViewpagerAdapter = new MainViewPagerAdapter(getFragmentManager(), titles, fragments);
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
