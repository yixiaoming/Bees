package org.yxm.bees.module.wanandroid.tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.yxm.bees.base.BaseMvpFragment;

/**
 * Wan 每个tablayout的item显示的Fragment
 */
public class WanTabFragment extends BaseMvpFragment<IWanTabView, WanTabPresenter> {

    public static final String BUNDLE_KEY_AUTHORID = "authorid";

    private int mAuthorId;

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
        if (savedInstanceState != null) {
            mAuthorId = savedInstanceState.getInt(BUNDLE_KEY_AUTHORID, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
