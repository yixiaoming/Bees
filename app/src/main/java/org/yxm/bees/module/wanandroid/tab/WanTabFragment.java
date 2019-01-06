package org.yxm.bees.module.wanandroid.tab;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.bees.entity.wan.WanArticleEntity;
import org.yxm.bees.module.wanandroid.WanViewModel;
import org.yxm.lib.views.PullToRefreshLayout;

import java.util.Collections;
import java.util.List;

/**
 * Wan 每个tablayout的item显示的Fragment
 */
public class WanTabFragment extends BaseMvpFragment<IWanTabView, WanTabPresenter> {

    public static final String BUNDLE_KEY_AUTHORID = "authorid";
    private static final String TAG = "WanTabFragment";

    private int mAuthorId;

    private PullToRefreshLayout mPullRefreshLayout;
    private RecyclerView mRecyclerView;
    private WanTabRecyclerAdapter mWanTabAdapter;

    private WanViewModel mWanViewModel;

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
        Log.d(TAG, "onCreate: ");

        if (getArguments() != null) {
            mAuthorId = getArguments().getInt(BUNDLE_KEY_AUTHORID, 0);
        }
        if (mAuthorId != 0) {
            mWanViewModel = ViewModelProviders.of(this).get(WanViewModel.class);
            mWanViewModel.getWanArticleLiveData(mAuthorId).observe(this, new Observer<List<WanArticleEntity>>() {
                @Override
                public void onChanged(@Nullable List<WanArticleEntity> datas) {
                    mWanTabAdapter.setDatas(datas);
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.wantab_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initDatas();
    }

    private void initViews(View rootView) {
        mPullRefreshLayout = rootView.findViewById(R.id.wantab_pull_to_refresh_layout);
        mRecyclerView = rootView.findViewById(R.id.wantab_recyclerview);

        mWanTabAdapter = new WanTabRecyclerAdapter(Collections.emptyList());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mWanTabAdapter);
    }

    private void initDatas() {
        if (mAuthorId != 0) {
            mWanViewModel.loadWanArticles(mAuthorId);
        }
    }
}
