package org.yxm.bees.module.main;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import org.yxm.bees.R;
import org.yxm.bees.module.music.MusicFragment;
import org.yxm.bees.module.personal.PersonalFragment;
import org.yxm.bees.module.wanandroid.WanFragment;
import org.yxm.bees.utils.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import org.yxm.modules.base.mvp.BaseMvpActivity;
import org.yxm.modules.base.mvp.BasePresenter;
import org.yxm.modules.gank.GankFragment;
import org.yxm.modules.kaiyan.KaiyanFragment;

public class MainActivity extends BaseMvpActivity
        implements IMainView {

    private BottomNavigationView mBottomNavView;
    private Fragment mCurrentFragment;

    public static final String TAG_FRAGMENT_WAN = "tag_fragment_wan";
    public static final String TAG_FRAGMENT_GANK = "tag_fragment_gank";
    public static final String TAG_FRAGMENT_VIDEO = "tag_fragment_video";
    public static final String TAG_FRAGMENT_MUSIC = "tag_fragment_music";
    public static final String TAG_FRAGMENT_PERSONAL = "tag_fragment_personal";

    private static List<String> mFragmetnIndex = new ArrayList<>(4);

    static {
        mFragmetnIndex.add(TAG_FRAGMENT_GANK);
        mFragmetnIndex.add(TAG_FRAGMENT_VIDEO);
        mFragmetnIndex.add(TAG_FRAGMENT_WAN);
        mFragmetnIndex.add(TAG_FRAGMENT_MUSIC);
        mFragmetnIndex.add(TAG_FRAGMENT_PERSONAL);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
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
                    JCVideoPlayer.releaseAllVideos();
                    switch (item.getItemId()) {
                        case R.id.action_wan:
                            showFragment(TAG_FRAGMENT_WAN);
                            break;
                        case R.id.action_gank:
                            showFragment(TAG_FRAGMENT_GANK);
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
        showFragment(TAG_FRAGMENT_GANK);
    }

    private void showFragment(String fragmentTag) {
        if (fragmentTag.isEmpty()) {
            return;
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 设置fragment切换动画
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
            mBottomNavView.getMenu().getItem(nextIndex).setChecked(true);
        }

        // 要显示的fragment
        if (fragment == null) {
            if (fragmentTag.equals(TAG_FRAGMENT_WAN)) {
                fragment = WanFragment.newInstance();
                transaction.add(R.id.main_content_framelayout, fragment, fragmentTag);
            } else if (fragmentTag.equals(TAG_FRAGMENT_GANK)) {
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
        // 要隐藏的fragment
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
