package org.yxm.bees.module.wanandroid;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * WanTablayout 使用pageradapter
 */
public class WanPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;
    private List<Fragment> mFragments;

    public WanPagerAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }
}
