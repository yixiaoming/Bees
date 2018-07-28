package org.yxm.bees.module.news.tab.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.yxm.bees.R;

/**
 * Created by yxm on 2018.7.28.
 */
public class ViewHolderBigImg extends RecyclerView.ViewHolder {

    public ImageView mPhoto;
    public TextView mAuthor;
    public TextView mDate;

    public ViewHolderBigImg(View itemView) {
        super(itemView);
        mPhoto = itemView.findViewById(R.id.img);
        mAuthor = itemView.findViewById(R.id.author);
        mDate = itemView.findViewById(R.id.date);
    }
}