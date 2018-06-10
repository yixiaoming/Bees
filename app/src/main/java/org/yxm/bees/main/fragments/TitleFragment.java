package org.yxm.bees.main.fragments;

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
import org.yxm.bees.main.adapter.TitleRecyclerAdapter;
import org.yxm.bees.main.contract.TitleContract;
import org.yxm.bees.main.presenter.TitlePresenter;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/9.
 */

public class TitleFragment extends Fragment implements TitleContract.View {

    public static final String ARGS_TITLE = "title";

    private RecyclerView mRecyclerview;
    private TitleRecyclerAdapter mAdapter;
    private TitleContract.Presenter mPresenter;

    public static TitleFragment newInstance(String title) {
        TitleFragment fragment = new TitleFragment();

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
        mPresenter = new TitlePresenter(this);
        mPresenter.start();
    }

    private void initViews(View root) {
        mRecyclerview = root.findViewById(R.id.title_recyclerview);
    }

    @Override
    public void setPresenter(TitleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initDatas(List<String> datas) {
        mAdapter = new TitleRecyclerAdapter(datas);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
