package org.yxm.bees.module.kaiyan.tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.bees.entity.kaiyan.KaiyanCategory;
import org.yxm.bees.entity.kaiyan.KaiyanVideoItem;
import org.yxm.bees.module.common.SwipeStopVideoListener;
import org.yxm.bees.util.LogUtil;
import org.yxm.bees.util.ToastUtil;

import java.util.List;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanTabFragment extends BaseMvpFragment<IKaiyanTabView, KaiyanTabPresenter>
        implements IKaiyanTabView {

    private static final java.lang.String TAG = "KaiyanTabFragment";
    public static final String ARGS_ID = "id";
    public static final String ARGS_NAME = "name";

    private int mTabId;

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

        mRefresyLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
//        mRefresyLayout.setOnRefreshListener(() -> mPresenter.onRefresh(mType));

        mAdapter = new KaiyanRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setOnScrollListener(new SwipeStopVideoListener());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefresyLayout.setRefreshing(true);
        mPresenter.initData(mTabId);
    }


    @Override
    public void initDataSuccess(List<KaiyanVideoItem> datas) {
        Log.d(TAG, "initDataSuccess: " + datas.size());
        mRefresyLayout.setRefreshing(false);
        mAdapter.addDataFront(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initDataFailed(Throwable t) {
        mRefresyLayout.setRefreshing(false);
        ToastUtil.s(getContext(), "加载开眼数据失败：" + t);
    }
}
