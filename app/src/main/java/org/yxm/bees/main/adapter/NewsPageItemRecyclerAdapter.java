package org.yxm.bees.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yxm.bees.R;
import org.yxm.bees.pojo.Blog;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by yixiaoming on 2018/6/10.
 */

public class NewsPageItemRecyclerAdapter extends RecyclerView.Adapter<NewsPageItemRecyclerAdapter.ViewHolder> {

    private List<Blog> mDatas;

    public NewsPageItemRecyclerAdapter(List<Blog> datas) {
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blog_recycler_item_view, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Blog blog = mDatas.get(position);
        holder.mBlogTitle.setText(blog.title);
        holder.mBlogContent.setText(blog.content);
        holder.mBlogAuthor.setText(blog.author);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        holder.mBlogDate.setText(sdf.format(blog.date));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void insertFront(List<Blog> datas) {
        mDatas.addAll(0, datas);
    }

    public void insertEnd(List<Blog> datas) {
        mDatas.addAll(datas);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mBlogTitle;
        public TextView mBlogContent;
        public TextView mBlogAuthor;
        public TextView mBlogDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mBlogTitle = itemView.findViewById(R.id.blog_title_text);
            mBlogContent = itemView.findViewById(R.id.blog_content_text);
            mBlogAuthor = itemView.findViewById(R.id.blog_author_text);
            mBlogDate = itemView.findViewById(R.id.blog_date_text);
        }
    }
}
