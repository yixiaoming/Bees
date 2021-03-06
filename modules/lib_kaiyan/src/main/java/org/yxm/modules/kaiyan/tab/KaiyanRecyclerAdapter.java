package org.yxm.modules.kaiyan.tab;

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
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.yxm.modules.base.activity.WebViewActivity;
import org.yxm.modules.base.glide.GlideApp;
import org.yxm.modules.kaiyan.R;
import org.yxm.modules.kaiyan.entity.KaiyanVideoItem;

/**
 * Created by yxm on 2018.7.29.
 */

public class KaiyanRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    implements View.OnClickListener {

  private static final String TAG = "KaiyanRecyclerAdapter";

  private Context mContext;
  private List<KaiyanVideoItem> mDatas;

  private OnItemClickListener mOnItemClickListener;

  public KaiyanRecyclerAdapter() {
    this.mDatas = new ArrayList<>();
    mOnItemClickListener = new OnItemClickListener() {
      @Override
      public void onClick(View v) {
        int position = (int) v.getTag();
        Intent intent = new Intent(mContext, WebViewActivity.class);
        intent.putExtra(WebViewActivity.PARAM_URL, mDatas.get(position).data.webUrl.raw);
        mContext.startActivity(intent);
      }
    };
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (mContext == null) {
      mContext = parent.getContext();
    }
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.kaiyan_recycler_item_video_layout,
            parent, false);

    return new VideoViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof VideoViewHolder) {
      holder.itemView.setTag(position);
      bindVideoViewHolder((VideoViewHolder) holder, position);
    }
  }

  private void bindVideoViewHolder(VideoViewHolder holder, int position) {
    KaiyanVideoItem item = mDatas.get(position);
    try {
      holder.author.setText(item.data.author.name);

      GlideApp.with(holder.authorImg.getContext())
          .load(item.data.author.icon)
          .into(holder.authorImg);
      holder.date.setText(timeStamp2Date(item.data.date, "yyyy-MM-dd"));
      holder.desc.setText(item.data.description);
      boolean setup = holder.videoPlayer.setUp(item.data.playUrl,
          JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
      holder.videoPlayer.setKeepScreenOn(false);
      if (setup) {
        GlideApp.with(holder.videoPlayer.getContext())
            .load(item.data.cover.detail)
            .into(holder.videoPlayer.thumbImageView);
      }
    } catch (NullPointerException e) {
      holder.itemView.setVisibility(View.GONE);
    }
  }

  @Override
  public int getItemCount() {
    return mDatas.size();
  }

  public void addDataFront(List<KaiyanVideoItem> datas) {
    mDatas.addAll(0, datas);
  }

  public void addDatasEnd(List<KaiyanVideoItem> datas) {
    mDatas.addAll(datas);
  }

  @Override
  public void onClick(View v) {
    mOnItemClickListener.onClick(v);
  }

  private static class VideoViewHolder extends RecyclerView.ViewHolder {

    public JCVideoPlayerStandard videoPlayer;
    public TextView author;
    public ImageView authorImg;
    public TextView date;
    public TextView desc;

    public VideoViewHolder(View itemView) {
      super(itemView);
      videoPlayer = itemView.findViewById(R.id.video);
      author = itemView.findViewById(R.id.author);
      authorImg = itemView.findViewById(R.id.author_img);
      date = itemView.findViewById(R.id.date);
      desc = itemView.findViewById(R.id.desc);
    }
  }

  private interface OnItemClickListener extends View.OnClickListener {

  }

  public static String timeStamp2Date(String timestampString, String formats) {
    if (TextUtils.isEmpty(formats)) {
      formats = "yyyy-MM-dd HH:mm:ss.SSS";
    }
    Long timestamp = Long.parseLong(timestampString);
    String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
    return date;
  }
}
