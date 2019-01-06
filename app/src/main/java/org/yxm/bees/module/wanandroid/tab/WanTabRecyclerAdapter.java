package org.yxm.bees.module.wanandroid.tab;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yxm.bees.R;
import org.yxm.bees.entity.wan.WanArticleEntity;

import java.util.List;


public class WanTabRecyclerAdapter extends RecyclerView.Adapter<WanTabRecyclerAdapter.ViewHolder> {

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

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.wantab_recyclerview_item_title);
        }
    }
}
