package org.yxm.bees.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseActivity;
import org.yxm.bees.fragment.MediaTabFragment;
import org.yxm.bees.fragment.NewsTabFragment;
import org.yxm.bees.fragment.PhotoTabFragment;
import org.yxm.bees.fragment.VideoTabFragment;
import org.yxm.bees.util.T;
import org.yxm.bees.view.helper.BottomNavigationViewHelper;


/**
 * 主Activity
 * Created by yixiaoming on 2018/4/6.
 */

public class MainActivity extends BaseActivity {

    private static final int FRAGMENT_NEWS = 0x1;
    private static final int FRAGMENT_PHOTO = 0x2;
    private static final int FRAGMENT_VIDEO = 0x3;
    private static final int FRAGMENT_MEDIA = 0x4;
    private static final java.lang.String LAST_SELECT_TAB = "last_select_tab";
    private Toolbar mToolBar;
    private BottomNavigationView mBottomNavigation;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private NewsTabFragment mNewsTabFragment;
    private PhotoTabFragment mPhotoTabFragment;
    private VideoTabFragment mVideoTabFragment;
    private MediaTabFragment mMediaTabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        if (savedInstanceState != null) {
            FragmentManager fm = getSupportFragmentManager();
            mNewsTabFragment = (NewsTabFragment) fm.findFragmentByTag(NewsTabFragment.getFragmentTag());
            mPhotoTabFragment = (PhotoTabFragment) fm.findFragmentByTag(PhotoTabFragment.getFragmentTab());
            mVideoTabFragment = (VideoTabFragment) fm.findFragmentByTag(VideoTabFragment.getFragmentTab());
            mMediaTabFragment = (MediaTabFragment) fm.findFragmentByTag(MediaTabFragment.getFragmentTab());
            mBottomNavigation.setSelectedItemId(savedInstanceState.getInt(LAST_SELECT_TAB));
        } else {
            showFragment(FRAGMENT_NEWS);
        }
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setOnMenuItemClickListener(new OnToolbarItemSelectedListner());

        mBottomNavigation = findViewById(R.id.bottom_navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(new OnTabSelectedListener());
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigation);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.drawer_open, R.string.drawer_close
        );
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new OnDrawerItemSelectedListener());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * 显示tab fragment
     * @param index
     */
    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideTabFragments(ft);

        switch (index) {
            case FRAGMENT_NEWS:
                setToolbarTitle(R.string.title_news);
                if (mNewsTabFragment == null) {
                    mNewsTabFragment = NewsTabFragment.getInstance();
                    ft.add(R.id.container, mNewsTabFragment, NewsTabFragment.getFragmentTag());
                } else {
                    ft.show(mNewsTabFragment);
                }
                break;
            case FRAGMENT_PHOTO:
                setToolbarTitle(R.string.title_photo);
                if (mPhotoTabFragment == null) {
                    mPhotoTabFragment = PhotoTabFragment.getInstance();
                    ft.add(R.id.container, mPhotoTabFragment, PhotoTabFragment.getFragmentTab());
                } else {
                    ft.show(mPhotoTabFragment);
                }
                break;
            case FRAGMENT_VIDEO:
                setToolbarTitle(R.string.title_video);
                if (mVideoTabFragment == null) {
                    mVideoTabFragment = VideoTabFragment.getInstance();
                    ft.add(R.id.container, mVideoTabFragment, VideoTabFragment.getFragmentTab());
                } else {
                    ft.show(mVideoTabFragment);
                }
                break;
            case FRAGMENT_MEDIA:
                setToolbarTitle(R.string.title_media);
                if (mMediaTabFragment == null) {
                    mMediaTabFragment = MediaTabFragment.getInstance();
                    ft.add(R.id.container, mMediaTabFragment, MediaTabFragment.getFragmentTab());
                } else {
                    ft.show(mMediaTabFragment);
                }
                break;
            default:
                break;
        }
        ft.commit();
    }

    /**
     * 隐藏所有的tab内容
     * @param ft
     */
    private void hideTabFragments(FragmentTransaction ft) {
        if (mNewsTabFragment != null) {
            ft.hide(mNewsTabFragment);
        }
        if (mPhotoTabFragment != null) {
            ft.hide(mPhotoTabFragment);
        }
        if (mVideoTabFragment != null) {
            ft.hide(mVideoTabFragment);
        }
        if (mMediaTabFragment != null) {
            ft.hide(mMediaTabFragment);
        }
    }

    /**
     * 设置toorbar标题
     * @param strId
     */
    private void setToolbarTitle(@NonNull int strId) {
        if (mToolBar == null) return;
        mToolBar.setTitle(getResources().getString(strId));
    }

    /**
     * 底部tab选中点击事件
     */
    class OnTabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_news:
                    showFragment(FRAGMENT_NEWS);
                    break;
                case R.id.action_photo:
                    showFragment(FRAGMENT_PHOTO);
                    break;
                case R.id.action_video:
                    showFragment(FRAGMENT_VIDEO);
                    break;
                case R.id.action_media:
                    showFragment(FRAGMENT_MEDIA);
                    break;
            }
            return true;
        }
    }

    /**
     * 侧边栏项目选中
     */
    class OnDrawerItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return true;
        }
    }

    /**
     * toolbar item 点击事件
     */
    class OnToolbarItemSelectedListner implements Toolbar.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_search:
                    T.s(MainActivity.this, R.string.action_search);
                    break;
            }
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }
}
