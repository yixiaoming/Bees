package org.yxm.modules.oneapi;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import org.yxm.lib.base.components.BaseFragment;
import org.yxm.modules.base.utils.LogUtils;

public class OneApiFragment extends BaseFragment<OneApiView, OneApiPresenter>
    implements OneApiView {

  public static final String TAG = "OneApiFragment";

  private OneApiViewModel mViewModel;

  public static OneApiFragment newInstance() {
    OneApiFragment fragment = new OneApiFragment();
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(OneApiViewModel.class);
    mViewModel.getAuthers().observe(this, new Observer<List<String>>() {
      @Override
      public void onChanged(@Nullable List<String> strings) {

      }
    });
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.oneapi_fragment, container, false);
    initViews(root);
    return root;
  }

  private void initViews(View root) {
    LogUtils.d(TAG, "initViews");
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter.initData();
  }

  @Override
  protected OneApiPresenter initPresenter() {
    return new OneApiPresenter();
  }

  @Override
  public void onInitData() {
    LogUtils.d(TAG, "onInitData");
  }
}
