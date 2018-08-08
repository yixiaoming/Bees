package org.yxm.bees.module.gank.tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.entity.gankio.GankTabEntity;
import org.yxm.bees.module.common.PullRefreshLoadMoreLayout;
import org.yxm.bees.module.common.SwipeStopVideoListener;
import org.yxm.bees.util.LogUtil;
import org.yxm.bees.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class GankTabFragment extends BaseMvpFragment<IGankTabView, GankTabPresenter>
        implements IGankTabView {

    public static final String TAG = "GankTabFragment";

    public static final String ARGS_TYPE = "type";

    private PullRefreshLoadMoreLayout mRefreshLayout;
    private RecyclerView mRecyclerview;
    private GankTabRecyclerAdapter mAdapter;

    private String mType;

    public static Fragment newInstance(GankTabEntity tabinfo) {
        GankTabFragment fragment = new GankTabFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ARGS_TYPE, tabinfo.type);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected GankTabPresenter createPresenter() {
        return new GankTabPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtil.e(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.main_title_fragment_layout, container, false);
        initViews(view);

        if (getArguments() != null) {
            mType = getArguments().getString(ARGS_TYPE);
        } else {
            throw new RuntimeException("no type of this tab fragment");
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.loadData(mType);
    }

    private void initViews(View root) {
        mRefreshLayout = root.findViewById(R.id.refresh_loadmore_layout);
        mRefreshLayout.setRefreshLoadmoreListener(new PullRefreshLoadMoreLayout.IRefreshLoadmoreListener() {
            @Override
            public void onRefresh() {
                mPresenter.doRefresh(mType);
            }

            @Override
            public void onLoadmore() {
                mPresenter.doLoadmore(mType);
            }
        });
        mRecyclerview = root.findViewById(R.id.recyclerview);

        mAdapter = new GankTabRecyclerAdapter(new ArrayList<>());
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.addOnScrollListener(new SwipeStopVideoListener());
    }

    @Override
    public void onInitDataSuccess(List<GankEntity> datas) {
        mAdapter.insertFront(datas);
        mAdapter.notifyDataSetChanged();
        if (datas == null || datas.size() == 0) {
            mRefreshLayout.setRefreshing(true);
            mPresenter.doRefresh(mType);
        }
    }

    @Override
    public void onInitDataFailed(Throwable e) {
        ToastUtil.s(getContext(), e.toString());
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshSuccess(List<GankEntity> datas) {
        mAdapter.insertFront(datas);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshFailed(Throwable e) {
        ToastUtil.s(getContext(), e.toString());
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreSuccess(List<GankEntity> datas) {
        mAdapter.insertEnd(datas);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setLoadingmoreing(false);
    }

    @Override
    public void onLoadMoreFailed(Throwable e) {
        ToastUtil.s(getContext(), e.toString());
        mRefreshLayout.setLoadingmoreing(false);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.s(getContext(), msg);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
