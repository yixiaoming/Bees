package org.yxm.bees.module.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpActivity;
import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.module.news.NewsFragment;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends BaseMvpActivity
        implements IMainView {

    private BottomNavigationView mBottomNavView;
    private Fragment mCurrentFragment;

    public static final String TAG_FRAGMENT_NEWS = "tag_fragment_news";
    public static final String TAG_FRAGMENT_PHOTO = "tag_fragment_photo";
    public static final String TAG_FRAGMENT_VIDEO = "tag_fragment_video";
    public static final String TAG_FRAGMENT_PERSONAL = "tag_fragment_personal";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        initViews();
    }

    private void initViews() {
        mBottomNavView = findViewById(R.id.main_bottom_nav_bar);
        mBottomNavView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_news:
                            showFragment(TAG_FRAGMENT_NEWS);
                            return true;
                        case R.id.action_photo:
                            showFragment(TAG_FRAGMENT_PHOTO);
                            return true;
                        case R.id.action_video:
                            showFragment(TAG_FRAGMENT_VIDEO);
                            return true;
                        case R.id.action_personal:
                            showFragment(TAG_FRAGMENT_PERSONAL);
                            return true;
                    }
                    return false;
                });
        mBottomNavView.getMenu().getItem(0).setCheckable(true);
        showFragment(TAG_FRAGMENT_NEWS);
    }

    private void showFragment(String fragmentTag) {
        if (fragmentTag.isEmpty()) {
            return;
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment == null) {
            if (fragmentTag.equals(TAG_FRAGMENT_NEWS)) {
                fragment = NewsFragment.newInstance();
                transaction.add(R.id.main_content_framelayout, fragment, fragmentTag);
            } else if (fragmentTag.equals(TAG_FRAGMENT_PHOTO)) {
                fragment = NewsFragment.newInstance();
                transaction.add(R.id.main_content_framelayout, fragment, fragmentTag);
            } else if (fragmentTag.equals(TAG_FRAGMENT_VIDEO)) {
                fragment = NewsFragment.newInstance();
                transaction.add(R.id.main_content_framelayout, fragment, fragmentTag);
            } else if (fragmentTag.equals(TAG_FRAGMENT_PERSONAL)) {
                fragment = NewsFragment.newInstance();
                transaction.add(R.id.main_content_framelayout, fragment, fragmentTag);
            }
        }
        if (mCurrentFragment != null) {
            transaction.hide(mCurrentFragment);
        }
        transaction.show(fragment);
        mCurrentFragment = fragment;
        transaction.commit();
    }

    @Override
    protected BasePresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();
    }
}
