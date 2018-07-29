package org.yxm.bees.module.kaiyan;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> mTitles;
    private List<Fragment> mFragments;

    public KaiyanPagerAdapter(FragmentManager childFragmentManager,
                              List<String> titles, List<Fragment> fragments) {
        super(childFragmentManager);
        mTitles = titles;
        mFragments = fragments;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
