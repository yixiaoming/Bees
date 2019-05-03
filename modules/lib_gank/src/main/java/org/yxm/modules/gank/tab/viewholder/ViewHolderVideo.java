package org.yxm.modules.gank.tab.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import org.yxm.modules.gank.R;

/**
 * Created by yxm on 2018.7.28.
 */

public class ViewHolderVideo extends RecyclerView.ViewHolder {

    public JCVideoPlayerStandard videoPlayer;
    public TextView mAuthor;
    public TextView mDate;

    public ViewHolderVideo(View itemView) {
        super(itemView);
        videoPlayer = itemView.findViewById(R.id.video);
        mAuthor = itemView.findViewById(R.id.author);
        mDate = itemView.findViewById(R.id.date);
    }
}
