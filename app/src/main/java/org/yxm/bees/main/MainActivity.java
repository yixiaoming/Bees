package org.yxm.bees.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseActivity;
import org.yxm.bees.main.fragments.NewsFragment;


/**
 * 主Activity
 * Created by yixiaoming on 2018/4/6.
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        NewsFragment newsFragment = (NewsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_content_framelayout);
        if (newsFragment == null) {
            newsFragment = NewsFragment.newInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.main_content_framelayout, newsFragment);
            transaction.commit();
        }
    }
}
