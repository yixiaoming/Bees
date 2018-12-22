package org.yxm.bees.module.wanandroid.tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.lib.views.PullToRefreshLayout;

import java.util.List;

/**
 * Wan 每个tablayout的item显示的Fragment
 */
public class WanTabFragment extends BaseMvpFragment<IWanTabView, WanTabPresenter>
        implements IWanTabView {

    public static final String BUNDLE_KEY_AUTHORID = "authorid";

    private int mAuthorId;

    private PullToRefreshLayout mPullRefreshLayout;
    private RecyclerView mRecyclerView;

    private WanTabRecyclerAdapter mWanTabAdapter;

    public static WanTabFragment newInstance(int authorId) {
        Bundle args = new Bundle();
        args.putInt(BUNDLE_KEY_AUTHORID, authorId);
        WanTabFragment fragment = new WanTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected WanTabPresenter createPresenter() {
        return new WanTabPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAuthorId = getArguments().getInt(BUNDLE_KEY_AUTHORID, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wantab_fragment_layout, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        mPullRefreshLayout = rootView.findViewById(R.id.wantab_pull_to_refresh_layout);
        mRecyclerView = rootView.findViewById(R.id.wantab_recyclerview);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.startLoadData(mAuthorId);
    }


    @Override
    public void onLoadDataFinish(List<WanArticleEntity> datas) {
        mWanTabAdapter = new WanTabRecyclerAdapter(datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mWanTabAdapter);
        mRecyclerView.postInvalidate();
    }
}
