package org.yxm.bees.module.kaiyan.tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.bees.entity.kaiyan.KaiyanCategory;
import org.yxm.bees.util.LogUtil;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanTabFragment extends BaseMvpFragment<IKaiyanTabView, KaiyanTabPresenter> {

    private static final java.lang.String TAG = "KaiyanTabFragment";
    public static final String ARGS_ID = "id";
    public static final String ARGS_NAME = "name";

    private int mTabId;
    private String mTabName;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefresyLayout;
    private KaiyanRecyclerAdapter mAdapter;


    @Override
    protected KaiyanTabPresenter createPresenter() {
        return new KaiyanTabPresenter();
    }

    public static Fragment newInstance(KaiyanCategory category) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARGS_ID, category.id);
        bundle.putString(ARGS_NAME, category.name);
        KaiyanTabFragment fragment = new KaiyanTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtil.e(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.main_title_fragment_layout, container, false);
        initViews(view);

        if (getArguments() != null) {
            mTabId = getArguments().getInt(ARGS_ID);
        } else {
            throw new RuntimeException("no type of this tab fragment");
        }
        return view;
    }

    private void initViews(View root) {
        mRefresyLayout = root.findViewById(R.id.swiperefresh_layout);
        mRecyclerView = root.findViewById(R.id.recyclerview);

        mAdapter = new KaiyanRecyclerAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.initData(mTabId);
    }


}
