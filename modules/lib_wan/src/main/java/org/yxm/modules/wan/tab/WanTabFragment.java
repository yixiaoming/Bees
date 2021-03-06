package org.yxm.modules.wan.tab;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Collections;
import java.util.List;
import org.yxm.modules.wan.R;
import org.yxm.modules.wan.WanViewModel;
import org.yxm.modules.wan.entity.WanArticleEntity;

/**
 * Wan 每个tablayout的item显示的Fragment
 */
public class WanTabFragment extends Fragment {

  private static final String TAG = "WanTabFragment";
  public static final String BUNDLE_KEY_AUTHORID = "authorid";

  private int mAuthorId;

  private SwipeRefreshLayout mSwipeRefreshLayout;
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
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      mAuthorId = getArguments().getInt(BUNDLE_KEY_AUTHORID, 0);
    }
    if (mAuthorId != 0) {
      mWanViewModel = ViewModelProviders.of(this).get(WanViewModel.class);
      mWanViewModel.getWanArticleLiveData().observe(this,
          new Observer<List<WanArticleEntity>>() {
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
    return inflater.inflate(R.layout.wantab_fragment_layout, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view,
      @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViews(view);
    initDatas();
  }

  private void initViews(View rootView) {
    mSwipeRefreshLayout = rootView.findViewById(R.id.wantab_swipe_refresh_layout);
    mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
        WanTabFragment.this.onRefreshData();
      }
    });
    mRecyclerView = rootView.findViewById(R.id.wantab_recyclerview);

    mWanTabAdapter = new WanTabRecyclerAdapter(Collections.<WanArticleEntity>emptyList());
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.setAdapter(mWanTabAdapter);
  }

  private void initDatas() {
    if (mAuthorId != 0) {
      mWanViewModel
          .loadWanArticles(mAuthorId, mWanTabAdapter.getPage(), mWanTabAdapter.getPageSize(),
              mSwipeRefreshLayout);
    }
  }

  private void onRefreshData() {
    if (mAuthorId != 0) {
      mWanViewModel.onRefreshArticles(mAuthorId, mWanTabAdapter.getPage(), mSwipeRefreshLayout);
    }
  }
}
