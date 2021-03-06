package org.yxm.modules.kaiyan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import java.util.ArrayList;
import java.util.List;
import org.yxm.modules.base.mvp.BaseMvpFragment;
import org.yxm.modules.base.utils.ToastUtils;
import org.yxm.modules.kaiyan.entity.KaiyanCategory;
import org.yxm.modules.kaiyan.tab.KaiyanTabFragment;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanFragment extends BaseMvpFragment<IKaiyanView, KaiyanPresenter>
    implements IKaiyanView {

  private TabLayout mTablayout;
  private ViewPager mViewpager;

  private KaiyanPagerAdapter mViewpagerAdapter;

  @Override
  protected KaiyanPresenter createPresenter() {
    return new KaiyanPresenter(getContext());
  }

  public static KaiyanFragment newInstance() {
    KaiyanFragment fragment = new KaiyanFragment();
    Bundle bundle = new Bundle();
    fragment.setArguments(bundle);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.main_fragment_layout, container, false);
    initViews(view);
    return view;
  }

  private void initViews(View root) {
    mTablayout = root.findViewById(R.id.main_tablayout);
    mViewpager = root.findViewById(R.id.main_viewpager);
    mViewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override
      public void onPageSelected(int position) {
        super.onPageSelected(position);
        JCVideoPlayer.releaseAllVideos();
      }
    });
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter.loadTabFragments();
  }


  @Override
  public void initLocalDataSuccess(List<KaiyanCategory> categories) {
    List<String> titles = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
    for (KaiyanCategory category : categories) {
      titles.add(category.name);
      fragments.add(KaiyanTabFragment.newInstance(category));
    }
    // getChildFragmentManager()：fragment下面的子fragment，child的fragmentmanager
    mViewpagerAdapter = new KaiyanPagerAdapter(getChildFragmentManager(), titles, fragments);
    mTablayout.setTabsFromPagerAdapter(mViewpagerAdapter);
    mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    mTablayout.setupWithViewPager(mViewpager);

    mViewpager.setAdapter(mViewpagerAdapter);
    mViewpager.setOffscreenPageLimit(2);
  }

  @Override
  public void initLocalDataFailed(Throwable t) {
    mPresenter.loadNetCategories();
  }

  @Override
  public void initNetDataFailed(Throwable t) {
    ToastUtils.s(getContext(), "error:" + t);
  }

  @Override
  public void onPause() {
    super.onPause();
    JCVideoPlayer.releaseAllVideos();
  }
}
