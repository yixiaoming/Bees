package org.yxm.bees.module.wanandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.bees.entity.wan.WanTabEntity;
import org.yxm.bees.module.wanandroid.tab.WanTabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * WanAndroid 开放网站内容
 */
public class WanFragment extends BaseMvpFragment<IWanView, WanPresenter>
        implements IWanView {

    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private WanPagerAdapter mPagerAdapter;

    public static Fragment newInstance() {
        WanFragment fragment = new WanFragment();
        Bundle bundle = new Bundle();
        bundle.putString("", "");
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wan_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        mPresenter.initTablayout();
    }

    private void initViews(View root) {
        mTablayout = root.findViewById(R.id.wan_tablayout);
        mViewpager = root.findViewById(R.id.wan_viewpager);
    }

    @Override
    protected WanPresenter createPresenter() {
        return new WanPresenter();
    }


    @Override
    public void onInitTabLayout(List<WanTabEntity> tabs) {
        List<String> tabTitles = new ArrayList<>();
        List<Fragment> tabFragments = new ArrayList<>();

        for (WanTabEntity entity : tabs) {
            tabTitles.add(entity.name);
            tabFragments.add(WanTabFragment.newInstance(entity.id));
        }

        mTablayout.setTabsFromPagerAdapter(new WanPagerAdapter(
                getChildFragmentManager(), tabTitles, tabFragments
        ));
        mTablayout.setupWithViewPager(mViewpager);
        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}