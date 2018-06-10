package org.yxm.bees.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yxm.bees.R;

import java.util.List;

/**
 * Created by yixiaoming on 2018/6/10.
 */

public class NewsPageItemRecyclerAdapter extends RecyclerView.Adapter<NewsPageItemRecyclerAdapter.ViewHolder> {

    private List<String> mDatas;

    public NewsPageItemRecyclerAdapter(List<String> datas) {
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.title_recycler_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mText.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mText;

        public ViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.title_item_text);
        }
    }
}
