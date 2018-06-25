package org.yxm.bees.main.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.bees.main.adapter.NewsPageItemRecyclerAdapter;
import org.yxm.bees.main.presenter.NewsPageItemPresenter;
import org.yxm.bees.main.view.INewsPageItemView;
import org.yxm.bees.entity.Blog;
import org.yxm.bees.entity.TabInfo;
import org.yxm.bees.util.LogUtil;
import org.yxm.bees.util.ToastUtil;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/9.
 */

public class NewsPageItemFragment extends BaseMvpFragment<INewsPageItemView, NewsPageItemPresenter>
        implements INewsPageItemView {

    public static final String TAG = "NewsPageItemFragment";

    public static final String ARGS_TITLE = "title";

    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerview;

    private NewsPageItemRecyclerAdapter mAdapter;

    @Override
    protected NewsPageItemPresenter createPresenter() {
        return new NewsPageItemPresenter();
    }

    public static NewsPageItemFragment newInstance(TabInfo tabInfo) {
        NewsPageItemFragment fragment = new NewsPageItemFragment();

        Bundle args = new Bundle();
        args.putString(ARGS_TITLE, tabInfo.name);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LogUtil.e(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.main_title_fragment_layout, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.loadData();
    }

    private void initViews(View root) {
        mSwipeLayout = root.findViewById(R.id.news_swiperefresh_layout);
        mRecyclerview = root.findViewById(R.id.title_recyclerview);

        mSwipeLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onRefresh();
            }
        });
    }

    @Override
    public void initDatas(List<Blog> datas) {
        mAdapter = new NewsPageItemRecyclerAdapter(datas);
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
                    mPresenter.onLoadMore();
                }
            }
        });
    }

    @Override
    public void onRefreshDatas(List<Blog> datas) {
        mAdapter.insertFront(datas);
        mAdapter.notifyDataSetChanged();
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreDatas(List<Blog> datas) {
        mAdapter.insertEnd(datas);
        mAdapter.notifyDataSetChanged();
        ToastUtil.s(getContext(), "加载更多数据成功");
    }
}
