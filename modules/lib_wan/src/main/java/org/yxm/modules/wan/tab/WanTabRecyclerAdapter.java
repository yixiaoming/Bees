package org.yxm.modules.wan.tab;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import org.yxm.modules.wan.R;
import org.yxm.modules.wan.entity.WanArticleEntity;


public class WanTabRecyclerAdapter extends RecyclerView.Adapter<WanTabRecyclerAdapter.ViewHolder> {

  private static final int DEFAULT_PAGE_COUNT = 20;
  private List<WanArticleEntity> mDatas;

  public WanTabRecyclerAdapter(List<WanArticleEntity> datas) {
    mDatas = datas;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View itemView = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.wantab_recylerview_item_layout, viewGroup, false);
    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    WanArticleEntity entity = mDatas.get(i);
    viewHolder.mTitle.setText(entity.title);
  }

  @Override
  public int getItemCount() {
    return mDatas.size();
  }

  public void setDatas(List<WanArticleEntity> datas) {
    mDatas = datas;
    notifyDataSetChanged();
  }

  public int getPage() {
    return (mDatas.size() / DEFAULT_PAGE_COUNT) + 1;
  }

  public int getPageSize() {
    return DEFAULT_PAGE_COUNT;
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    TextView mTitle;

    ViewHolder(@NonNull View itemView) {
      super(itemView);
      mTitle = itemView.findViewById(R.id.wantab_recyclerview_item_title);
    }
  }
}
