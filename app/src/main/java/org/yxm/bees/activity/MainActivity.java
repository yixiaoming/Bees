package org.yxm.bees.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseActivity;


/**
 * 主Activity
 * Created by yixiaoming on 2018/4/6.
 */

public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
}
