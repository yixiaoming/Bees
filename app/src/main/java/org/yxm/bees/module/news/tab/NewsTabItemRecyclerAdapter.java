package org.yxm.bees.module.news.tab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.yxm.bees.R;
import org.yxm.bees.entity.gankio.GankEntity;

import java.util.List;

public class NewsTabItemRecyclerAdapter extends RecyclerView.Adapter<NewsTabItemRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<GankEntity> mDatas;

    public NewsTabItemRecyclerAdapter(List<GankEntity> datas) {
        this.mDatas = datas;
    }

    @Override
    public NewsTabItemRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_recycler_item_view, parent, false);
        NewsTabItemRecyclerAdapter.ViewHolder vh = new NewsTabItemRecyclerAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsTabItemRecyclerAdapter.ViewHolder holder, int position) {
        GankEntity item = mDatas.get(position);
        holder.mTitle.setText("");
        holder.mContent.setText(item.desc);
        holder.mAuthor.setText(item.who);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        holder.mDate.setText(sdf.format(item.publishedAt));
        holder.mDate.setText(item.publishedAt.substring(0, 10));
        if (item.images != null && item.images.size() > 0) {
            Glide.with(mContext).load(item.images.get(0)).into(holder.mPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void insertFront(List<GankEntity> datas) {
        mDatas.addAll(0, datas);
    }

    public void insertEnd(List<GankEntity> datas) {
        mDatas.addAll(datas);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mPhoto;
        public TextView mTitle;
        public TextView mContent;
        public TextView mAuthor;
        public TextView mDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mPhoto = itemView.findViewById(R.id.blog_photo);
            mTitle = itemView.findViewById(R.id.blog_title_text);
            mContent = itemView.findViewById(R.id.blog_content_text);
            mAuthor = itemView.findViewById(R.id.blog_author_text);
            mDate = itemView.findViewById(R.id.blog_date_text);
        }
    }
}
