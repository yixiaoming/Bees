package org.yxm.bees.module.personal;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.lib.views.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxm on 2018.8.11.
 */

public class PersonalFragment extends BaseMvpFragment<IPersonalView, PersonalPresenter>
        implements IPersonalView {

    public static final String TAG = "PersonalFragment";

    private PullToRefreshLayout mPullToRefreshLayout;
    private ListView mListView;
//    private RecyclerView mRecyclerView;

    @Override
    protected PersonalPresenter createPresenter() {
        return new PersonalPresenter();
    }

    public static PersonalFragment newInstance() {
        PersonalFragment fragment = new PersonalFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.personal_fragment_layout, container, false);
        initViews(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(View root) {
        mPullToRefreshLayout = root.findViewById(R.id.personal_pulltorefresh_layout);
//        mRecyclerView = root.findViewById(R.id.personal_recyclerview);
        mListView = root.findViewById(R.id.personal_listview);
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            list.add("item:" + i);
        }

        PersonalRecyclerAdapter adapter = new PersonalRecyclerAdapter(list);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.setAdapter(adapter);


        ArrayAdapter adapter2 = new ArrayAdapter<>(getContext(),
                R.layout.personal_list_item,
                R.id.personal_list_item_tv,
                list);
        mListView.setAdapter(adapter2);
        mPullToRefreshLayout.setPullToRefreshListener(() -> new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshLayout.resetHeader();
                Toast.makeText(getContext(), "正在刷新", Toast.LENGTH_SHORT).show();
            }
        }, 1 * 1000));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
