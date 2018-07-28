package org.yxm.bees.module.news.tab;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.yxm.bees.R;
import org.yxm.bees.base.GlideApp;
import org.yxm.bees.entity.gankio.GankEntity;
import org.yxm.bees.module.common.WebViewActivity;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class NewsTabItemRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<GankEntity> mDatas;
    private Context mContext;

    /**
     * 只创建一个item click lisener，通过position判断，无需每个view都创建，浪费空间
     */
    private OnItemClickListener mOnItemClickListener;

    public NewsTabItemRecyclerAdapter(List<GankEntity> datas) {
        this.mDatas = datas;
        this.mOnItemClickListener = position -> {
            String url = mDatas.get(position).url;
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(WebViewActivity.PARAM_URL, url);
            mContext.startActivity(intent);
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if (viewType == ItemType.no_image.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gank_recycler_item_view_no_img, parent, false);
            view.setOnClickListener(this);
            return new NewsTabItemRecyclerAdapter.ViewHolderNoImg(view);
        } else if (viewType == ItemType.one_image.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gank_recycler_item_view_one_img, parent, false);
            view.setOnClickListener(this);
            return new NewsTabItemRecyclerAdapter.ViewHolderOneImg(view);
        } else if (viewType == ItemType.three_image.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gank_recycler_item_view_three_img, parent, false);
            view.setOnClickListener(this);
            return new NewsTabItemRecyclerAdapter.ViewHolderThreeImg(view);
        } else if (viewType == ItemType.big_image.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gank_recycler_item_view_big_img, parent, false);
            view.setOnClickListener(this);
            return new NewsTabItemRecyclerAdapter.ViewHolderBigImg(view);
        } else if (viewType == ItemType.video.ordinal()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gank_recycler_item_view_video, parent, false);
            view.setOnClickListener(this);
            return new NewsTabItemRecyclerAdapter.ViewHolderVideo(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gank_recycler_item_view_no_img, parent, false);
            view.setOnClickListener(this);
            return new NewsTabItemRecyclerAdapter.ViewHolderNoImg(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // 设置tag，在item click中使用
        holder.itemView.setTag(position);
        if (holder instanceof ViewHolderNoImg) {
            bindNoImgViewHolder((ViewHolderNoImg) holder, position);
        } else if (holder instanceof ViewHolderOneImg) {
            bindOneImgViewHolder((ViewHolderOneImg) holder, position);
        } else if (holder instanceof ViewHolderThreeImg) {
            bindThreeImgViewHolder((ViewHolderThreeImg) holder, position);
        } else if (holder instanceof ViewHolderBigImg) {
            bindBigImgViewHolder((ViewHolderBigImg) holder, position);
        } else if (holder instanceof ViewHolderVideo) {
            bindVideoViewHolder((ViewHolderVideo) holder, position);
        }
    }

    private void bindNoImgViewHolder(ViewHolderNoImg holder, int position) {
        GankEntity item = mDatas.get(position);
        holder.mContent.setText(item.desc);
        holder.mAuthor.setText(item.who);
        holder.mDate.setText(item.publishedAt.substring(0, 10));
    }

    private void bindOneImgViewHolder(ViewHolderOneImg holder, int position) {
        GankEntity item = mDatas.get(position);
        holder.mContent.setText(item.desc);
        holder.mAuthor.setText(item.who);
        holder.mDate.setText(item.publishedAt.substring(0, 10));

        GlideApp.with(holder.mPhoto.getContext())
                .load(item.images.get(0))
                .into(holder.mPhoto);
    }

    private void bindThreeImgViewHolder(ViewHolderThreeImg holder, int position) {
        GankEntity item = mDatas.get(position);
        holder.mContent.setText(item.desc);
        holder.mAuthor.setText(item.who);
        holder.mDate.setText(item.publishedAt.substring(0, 10));

        GlideApp.with(holder.mImg1.getContext())
                .load(item.images.get(0))
                .into(holder.mImg1);

        GlideApp.with(holder.mImg2.getContext())
                .load(item.images.get(1))
                .into(holder.mImg2);

        GlideApp.with(holder.mImg3.getContext())
                .load(item.images.get(2))
                .into(holder.mImg3);
    }

    private void bindBigImgViewHolder(ViewHolderBigImg holder, int position) {
        GankEntity item = mDatas.get(position);
        holder.mAuthor.setText(item.who);
        holder.mDate.setText(item.publishedAt.substring(0, 10));

        GlideApp.with(holder.mPhoto.getContext())
                .load(item.url)
                .into(holder.mPhoto);
    }

    private void bindVideoViewHolder(ViewHolderVideo holder, int position) {
        GankEntity item = mDatas.get(position);
        holder.mAuthor.setText(item.who);
        holder.mDate.setText(item.publishedAt.substring(0, 10));

        boolean setup = holder.videoPlayer.setUp("http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4",
                JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
        holder.videoPlayer.setKeepScreenOn(false);
        if (setup) {
            GlideApp.with(holder.videoPlayer.getContext())
                    .load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg")
                    .into(holder.videoPlayer.thumbImageView);
        }
    }


    private enum ItemType {
        no_image,
        one_image,
        three_image,
        big_image,
        video,
    }

    @Override
    public int getItemViewType(int position) {
        GankEntity entity = mDatas.get(position);
        if (TextUtils.equals(entity.type, "福利")) {
            return ItemType.big_image.ordinal();
        }
        if (TextUtils.equals(entity.type, "休息视频")) {
            return ItemType.video.ordinal();
        }

        if (entity.images == null || entity.images.size() == 0) {
            return ItemType.no_image.ordinal();
        } else if (entity.images.size() < 3) {
            return ItemType.one_image.ordinal();
        } else {
            return ItemType.three_image.ordinal();
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

    private static class ViewHolderNoImg extends RecyclerView.ViewHolder {

        public TextView mContent;
        public TextView mAuthor;
        public TextView mDate;

        public ViewHolderNoImg(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.blog_content_text);
            mAuthor = itemView.findViewById(R.id.blog_author_text);
            mDate = itemView.findViewById(R.id.blog_date_text);
        }

    }

    private static class ViewHolderOneImg extends RecyclerView.ViewHolder {

        public ImageView mPhoto;
        public TextView mContent;
        public TextView mAuthor;
        public TextView mDate;

        public ViewHolderOneImg(View itemView) {
            super(itemView);
            mPhoto = itemView.findViewById(R.id.blog_photo);
            mContent = itemView.findViewById(R.id.blog_content_text);
            mAuthor = itemView.findViewById(R.id.blog_author_text);
            mDate = itemView.findViewById(R.id.blog_date_text);
        }

    }

    private static class ViewHolderThreeImg extends RecyclerView.ViewHolder {

        public ImageView mImg1;
        public ImageView mImg2;
        public ImageView mImg3;
        public TextView mContent;
        public TextView mAuthor;
        public TextView mDate;

        public ViewHolderThreeImg(View itemView) {
            super(itemView);
            mImg1 = itemView.findViewById(R.id.img1);
            mImg2 = itemView.findViewById(R.id.img2);
            mImg3 = itemView.findViewById(R.id.img3);
            mContent = itemView.findViewById(R.id.blog_content_text);
            mAuthor = itemView.findViewById(R.id.blog_author_text);
            mDate = itemView.findViewById(R.id.blog_date_text);
        }

    }

    private static class ViewHolderBigImg extends RecyclerView.ViewHolder {

        public ImageView mPhoto;
        public TextView mAuthor;
        public TextView mDate;

        public ViewHolderBigImg(View itemView) {
            super(itemView);
            mPhoto = itemView.findViewById(R.id.blog_photo);
            mAuthor = itemView.findViewById(R.id.blog_author_text);
            mDate = itemView.findViewById(R.id.blog_date_text);
        }
    }

    private static class ViewHolderVideo extends RecyclerView.ViewHolder {

        public JCVideoPlayerStandard videoPlayer;
        public TextView mAuthor;
        public TextView mDate;

        public ViewHolderVideo(View itemView) {
            super(itemView);
            videoPlayer = itemView.findViewById(R.id.video);
            mAuthor = itemView.findViewById(R.id.blog_author_text);
            mDate = itemView.findViewById(R.id.blog_date_text);
        }
    }

    /**
     * 点击事件
     */
    private interface OnItemClickListener {

        void onItemClick(int position);
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClick((Integer) v.getTag());
    }
}
