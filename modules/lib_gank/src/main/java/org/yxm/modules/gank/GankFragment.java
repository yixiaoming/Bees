package org.yxm.modules.gank;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import java.util.ArrayList;
import java.util.List;
import org.yxm.modules.base.mvp.BaseMvpFragment;
import org.yxm.modules.gank.entity.GankTabEntity;
import org.yxm.modules.gank.tab.GankTabFragment;

public class GankFragment extends BaseMvpFragment<IGankView, GankPresenter>
        implements IGankView {

    private static final String TAG = "GankFragment";

    private TabLayout mTablayout;
    private ViewPager mViewpager;

    private GankPagerAdapter mViewpagerAdapter;

    public static GankFragment newInstance() {
        return new GankFragment();
    }

    @Override
    protected GankPresenter createPresenter() {
        return new GankPresenter(getContext());
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
        mPresenter.loadData();
    }

    private void initViews(View root) {
        mTablayout = root.findViewById(R.id.main_tablayout);
        mViewpager = root.findViewById(R.id.main_viewpager);
        mViewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                JCVideoPlayer.releaseAllVideos();
            }
        });
    }

    @Override
    public void initDataView(List<GankTabEntity> tabInfos) {
        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();
        for (GankTabEntity tabinfo : tabInfos) {
            titles.add(tabinfo.name);
            fragments.add(GankTabFragment.newInstance(tabinfo));
        }
        // getChildFragmentManager()：fragment下面的子fragment，child的fragmentmanager
        mViewpagerAdapter = new GankPagerAdapter(getChildFragmentManager(), titles, fragments);
        mTablayout.setTabsFromPagerAdapter(mViewpagerAdapter);
        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTablayout.setupWithViewPager(mViewpager);

        mViewpager.setAdapter(mViewpagerAdapter);
        mViewpager.setOffscreenPageLimit(3);
    }
}
