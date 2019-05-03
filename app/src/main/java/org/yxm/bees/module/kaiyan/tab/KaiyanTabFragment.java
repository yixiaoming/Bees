package org.yxm.bees.module.kaiyan.tab;

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
import org.yxm.bees.entity.kaiyan.KaiyanCategory;
import org.yxm.bees.entity.kaiyan.KaiyanVideoItem;
import org.yxm.bees.module.common.SwipeStopVideoListener;

import java.util.List;
import org.yxm.modules.base.mvp.BaseMvpFragment;
import org.yxm.modules.base.utils.ToastUtils;
import org.yxm.modules.base.view.PullRefreshLoadMoreLayout;

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
    private PullRefreshLoadMoreLayout mRefreshLayout;
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
        mRefreshLayout = root.findViewById(R.id.refresh_loadmore_layout);
        mRefreshLayout.setRefreshLoadmoreListener(new PullRefreshLoadMoreLayout.IRefreshLoadmoreListener() {
            @Override
            public void onRefresh() {
                mPresenter.doRefresh(mTabId);
            }

            @Override
            public void onLoadmore() {
                mPresenter.doLoadmore(mTabId);
            }
        });
        mRecyclerView = root.findViewById(R.id.recyclerview);

        mAdapter = new KaiyanRecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setOnScrollListener(new SwipeStopVideoListener());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshLayout.setRefreshing(true);
        mPresenter.doInitLocalData(mTabId);
    }

    @Override
    public void onInitLocalDataSuccess(List<KaiyanVideoItem> datas) {
        mAdapter.addDataFront(datas);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onInitLocalDataFailed(Throwable t) {
        mPresenter.doLoadNetData(mTabId);
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onInitNetDataSuccess(List<KaiyanVideoItem> datas) {
        mAdapter.addDataFront(datas);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onInitNetDataFailed(Throwable t) {
        ToastUtils.s(getContext(), "加载开眼数据失败：" + t);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshSuccess(List<KaiyanVideoItem> datas) {
        mAdapter.addDataFront(datas);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshFailed(Throwable t) {
        ToastUtils.s(getContext(), "刷新数据失败：" + t);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadmoreSuccess(List<KaiyanVideoItem> datas) {
        mAdapter.addDatasEnd(datas);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadmoreFailed(Throwable t) {
        ToastUtils.s(getContext(), "加载更多数据失败：" + t);
        mRefreshLayout.setRefreshing(false);
    }
}
