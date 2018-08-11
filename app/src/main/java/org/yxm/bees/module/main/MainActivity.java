package org.yxm.bees.module.main;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpActivity;
import org.yxm.bees.base.BasePresenter;
import org.yxm.bees.module.kaiyan.KaiyanFragment;
import org.yxm.bees.module.gank.GankFragment;
import org.yxm.bees.module.music.MusicFragment;
import org.yxm.bees.module.personal.PersonalFragment;
import org.yxm.bees.util.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends BaseMvpActivity
        implements IMainView {

    private BottomNavigationView mBottomNavView;
    private Fragment mCurrentFragment;

    public static final String TAG_FRAGMENT_NEWS = "tag_fragment_news";
    public static final String TAG_FRAGMENT_VIDEO = "tag_fragment_video";
    public static final String TAG_FRAGMENT_MUSIC = "tag_fragment_music";
    public static final String TAG_FRAGMENT_PERSONAL = "tag_fragment_personal";

    private static List<String> mFragmetnIndex = new ArrayList<>(4);

    static {
        mFragmetnIndex.add(TAG_FRAGMENT_NEWS);
        mFragmetnIndex.add(TAG_FRAGMENT_VIDEO);
        mFragmetnIndex.add(TAG_FRAGMENT_MUSIC);
        mFragmetnIndex.add(TAG_FRAGMENT_PERSONAL);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        initViews();
    }

    private void initViews() {
        mBottomNavView = findViewById(R.id.main_bottom_nav_bar);
        // 设置选中颜色
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };
        int[] colors = new int[]{getResources().getColor(R.color.bottombar_text_color),
                getResources().getColor(R.color.bottombar_text_checked_color)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        mBottomNavView.setItemTextColor(csl);
        mBottomNavView.setItemIconTintList(csl);

        BottomNavigationViewHelper.disableShiftMode(mBottomNavView);
        mBottomNavView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_news:
                            showFragment(TAG_FRAGMENT_NEWS);
                            return true;
                        case R.id.action_video:
                            showFragment(TAG_FRAGMENT_VIDEO);
                            return true;
                        case R.id.action_music:
                            showFragment(TAG_FRAGMENT_MUSIC);
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
        if (mCurrentFragment != null) {
            String curTag = mCurrentFragment.getTag();
            int curIndex = mFragmetnIndex.indexOf(curTag);
            int nextIndex = mFragmetnIndex.indexOf(fragmentTag);
            if (curIndex > nextIndex) {
                transaction.setCustomAnimations(
                        R.anim.slide_left_in,
                        R.anim.slide_right_out,
                        R.anim.slide_right_in,
                        R.anim.slide_left_out);
            } else if (curIndex < nextIndex) {
                transaction.setCustomAnimations(
                        R.anim.slide_right_in,
                        R.anim.slide_left_out,
                        R.anim.slide_left_in,
                        R.anim.slide_right_out);
            }
        }

        if (fragment == null) {
            if (fragmentTag.equals(TAG_FRAGMENT_NEWS)) {
                fragment = GankFragment.newInstance();
                transaction.add(R.id.main_content_framelayout, fragment, fragmentTag);
            } else if (fragmentTag.equals(TAG_FRAGMENT_VIDEO)) {
                fragment = KaiyanFragment.newInstance();
                transaction.add(R.id.main_content_framelayout, fragment, fragmentTag);
            } else if (fragmentTag.equals(TAG_FRAGMENT_MUSIC)) {
                fragment = MusicFragment.newInstance();
                transaction.add(R.id.main_content_framelayout, fragment, fragmentTag);
            } else if (fragmentTag.equals(TAG_FRAGMENT_PERSONAL)) {
                fragment = PersonalFragment.newInstance();
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
