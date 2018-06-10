package org.yxm.bees.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseActivity;
import org.yxm.bees.main.contract.MainContract;
import org.yxm.bees.main.fragments.MainFragment;
import org.yxm.bees.main.presenter.MainPresenter;


/**
 * 主Activity
 * Created by yixiaoming on 2018/4/6.
 */

public class MainActivity extends BaseActivity {

    private ActionBar mActionBar;
    private Toolbar mToolbar;
    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        initToolbar();

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_framelayout);
        if (mainFragment == null) {
            mainFragment = MainFragment.newInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_framelayout, mainFragment);
            transaction.commit();
        }

        mPresenter = new MainPresenter(mainFragment);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
    }

}
