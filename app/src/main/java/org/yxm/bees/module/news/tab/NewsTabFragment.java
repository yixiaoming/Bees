package org.yxm.bees.module.news.tab;

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
import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.entity.gankio.GankTabEntity;
import org.yxm.bees.util.LogUtil;
import org.yxm.bees.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

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
        mSwipeLayout = root.findViewById(R.id.swiperefresh_layout);
        mRecyclerview = root.findViewById(R.id.recyclerview);

        mSwipeLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);

        mSwipeLayout.setOnRefreshListener(() -> mPresenter.onRefresh(mType));

        mAdapter = new NewsTabItemRecyclerAdapter(new ArrayList<>());
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.addOnScrollListener(new RecyclerScrollListener());
    }

    @Override
    public void initDatas(List<GankEntity> datas) {
        mAdapter.insertFront(datas);
        mAdapter.notifyDataSetChanged();
        if (datas == null || datas.size() == 0) {
            mSwipeLayout.setRefreshing(true);
            mPresenter.onRefresh(mType);
        }
        LogUtil.d("initDatas: type:" + mType + " size:" + datas.size());
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

    @Override
    public void onPause() {
        super.onPause();
    }

    private static class RecyclerScrollListener extends RecyclerView.OnScrollListener {
        private int lastTopItemPosition = -1;
        private int lastBottomItemPosition;
        private View firstView;
        private View lastView;
        private RecyclerView.LayoutManager layoutManager;

        private int curTopItemPosition;
        private int curBottomItemPosition;
        private int visibleCount;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                Glide.with(recyclerView).resumeRequests();
            } else {
                Glide.with(recyclerView).pauseRequests();
            }

            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                curTopItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                curBottomItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                visibleCount = layoutManager.getChildCount();

                if (lastTopItemPosition < curTopItemPosition) {
                    lastTopItemPosition = curTopItemPosition;
                    lastBottomItemPosition = curBottomItemPosition;
                    gcView(firstView);
                    firstView = recyclerView.getChildAt(0);
                    lastView = recyclerView.getChildAt(visibleCount - 1);
                } else if (lastBottomItemPosition > curBottomItemPosition) {
                    lastTopItemPosition = curTopItemPosition;
                    lastBottomItemPosition = curBottomItemPosition;
                    gcView(lastView);
                    firstView = recyclerView.getChildAt(0);
                    lastView = recyclerView.getChildAt(visibleCount - 1);
                }
            }
        }

        private void gcView(View view) {
            if (view != null && view.findViewById(R.id.video) != null) {
                JCVideoPlayerStandard player = view.findViewById(R.id.video);
                if (player.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING
                        || player.currentState == JCVideoPlayer.CURRENT_STATE_ERROR) {
                    player.setUiWitStateAndScreen(JCVideoPlayer.CURRENT_STATE_NORMAL);
                    JCVideoPlayer.releaseAllVideos();
                }

            }
        }
    }
}
