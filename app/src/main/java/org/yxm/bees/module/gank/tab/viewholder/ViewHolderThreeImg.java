package org.yxm.bees.module.gank.tab.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.yxm.bees.R;

/**
 * Created by yxm on 2018.7.28.
 */
public class ViewHolderThreeImg extends RecyclerView.ViewHolder {

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
        mContent = itemView.findViewById(R.id.desc);
        mAuthor = itemView.findViewById(R.id.author);
        mDate = itemView.findViewById(R.id.date);
    }

}