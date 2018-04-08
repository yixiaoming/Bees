package org.yxm.bees.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseFragment;

/**
 * Created by yixiaoming on 2018/4/6.
 */

public class VideoTabFragment extends BaseFragment {

    private static VideoTabFragment instance = null;

    @Deprecated
    public VideoTabFragment() {
    }

    // 不要使用构造方法，使用单例获取
    public static VideoTabFragment getInstance() {
        if (instance == null) {
            synchronized (PhotoTabFragment.class) {
                if (instance == null) {
                    instance = new VideoTabFragment();
                }
            }
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_news_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
    }

    public static String getFragmentTab() {
        return VideoTabFragment.class.getName();
    }
}
