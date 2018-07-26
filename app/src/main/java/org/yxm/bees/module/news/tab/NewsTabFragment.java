package org.yxm.bees.module.news.tab;

import android.icu.text.MessagePattern;
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

import com.bumptech.glide.Glide;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.bees.base.GlideApp;
import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.entity.gankio.GankTabEntity;
import org.yxm.bees.util.LogUtil;
import org.yxm.bees.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewsTabFragment extends BaseMvpFragment<INewsTabView, NewsTabPresenter>
        implements INewsTabView {

    public static final String TAG = "NewsTabFragment";

    public static final String ARGS_TYPE = "type";

    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerview;
    private NewsTabItemRecyclerAdapter mAdapter;

    private String mType;

    public static Fragment newInstance(GankTabEntity tabinfo) {
        NewsTabFragment fragment = new NewsTabFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ARGS_TYPE, tabinfo.type);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected NewsTabPresenter createPresenter() {
        return new NewsTabPresenter();
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
            throw new RuntimeException("no type of this tabfragment");
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.loadData(mType);
    }

    private void initViews(View root) {
        mSwipeLayout = root.findViewById(R.id.news_swiperefresh_layout);
        mRecyclerview = root.findViewById(R.id.title_recyclerview);

        mSwipeLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);

        mSwipeLayout.setOnRefreshListener(() -> mPresenter.onRefresh(mType));

        mAdapter = new NewsTabItemRecyclerAdapter(new ArrayList<GankEntity>());
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mRecyclerview.canScrollVertically(1)) {
                    mPresenter.onLoadMore(mType);
                }
            }
        });
    }

    @Override
    public void initDatas(List<GankEntity> datas) {
        mAdapter.insertFront(datas);
        mAdapter.notifyDataSetChanged();
        LogUtil.d("initDatas: finished:" + datas.size());
    }

    @Override
    public void initDatasFailed(Exception e) {
        LogUtil.d("initDatasFailed:" + e);
        ToastUtil.s(getContext(), e.toString());
    }

    @Override
    public void onRefreshSuccess(List<GankEntity> datas) {
        mAdapter.insertFront(datas);
        mAdapter.notifyDataSetChanged();
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshFailed(Exception e) {
        mSwipeLayout.setRefreshing(false);
        LogUtil.d("onLoadMoreFailed:" + e);
        ToastUtil.s(getContext(), e.toString());
    }

    @Override
    public void onLoadMoreSuccess(List<GankEntity> datas) {
        mAdapter.insertEnd(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreFailed(Exception e) {
        LogUtil.d("onLoadMoreFailed:" + e);
        ToastUtil.s(getContext(), e.toString());
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.s(getContext(), msg);
    }

}
