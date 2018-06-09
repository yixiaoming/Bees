package org.yxm.bees.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yxm.bees.R;

/**
 * Created by yixiaoming on 2018/6/9.
 */

public class MainFragment extends Fragment implements MainContract.View {

    public static final String MAIN_FRAGMENT_ID = "main_fragment_id";

    private ViewPager mViewpager;

    private MainContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment_layout, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mViewpager = view.findViewById(R.id.main_viewpager);
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
