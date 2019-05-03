package org.yxm.modules.gank.tab.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.yxm.modules.gank.R;

/**
 * Created by yxm on 2018.7.28.
 */

public class ViewHolderOneImg extends RecyclerView.ViewHolder {

    public ImageView mPhoto;
    public TextView mContent;
    public TextView mAuthor;
    public TextView mDate;

    public ViewHolderOneImg(View itemView) {
        super(itemView);
        mPhoto = itemView.findViewById(R.id.img);
        mContent = itemView.findViewById(R.id.desc);
        mAuthor = itemView.findViewById(R.id.author);
        mDate = itemView.findViewById(R.id.date);
    }

}