package org.yxm.bees.main.fragments;

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
import org.yxm.bees.main.adapter.NewsPageItemRecyclerAdapter;
import org.yxm.bees.main.contract.NewsPageItemContract;
import org.yxm.bees.main.presenter.NewsPageItemPresenter;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/9.
 */

public class NewsPageItemFragment extends BaseMvpFragment<NewsPageItemPresenter> implements NewsPageItemContract.View {

    public static final String ARGS_TITLE = "title";

    private RecyclerView mRecyclerview;

    private NewsPageItemRecyclerAdapter mAdapter;

    @Override
    protected NewsPageItemPresenter createPresenter() {
        return new NewsPageItemPresenter(this);
    }

    public static NewsPageItemFragment newInstance(String title) {
        NewsPageItemFragment fragment = new NewsPageItemFragment();

        Bundle args = new Bundle();
        args.putString(ARGS_TITLE, title);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_title_fragment_layout, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.get().start();
    }

    private void initViews(View root) {
        mRecyclerview = root.findViewById(R.id.title_recyclerview);
    }

    @Override
    public void initDatas(List<String> datas) {

        mAdapter = new NewsPageItemRecyclerAdapter(datas);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
