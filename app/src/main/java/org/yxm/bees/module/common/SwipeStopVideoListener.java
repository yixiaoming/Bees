package org.yxm.bees.module.common;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;

import org.yxm.bees.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class SwipeStopVideoListener extends RecyclerView.OnScrollListener {
    private int lastTopItemPosition = -1;
    private int lastBottomItemPosition;
    private View firstView;
    private View lastView;
    private RecyclerView.LayoutManager layoutManager;

    private int curTopItemPosition;
    private int curBottomItemPosition;
    private int visibleCount;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            Glide.with(recyclerView).resumeRequests();
        } else {
            Glide.with(recyclerView).pauseRequests();
        }

        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            curTopItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            curBottomItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            visibleCount = layoutManager.getChildCount();

            if (lastTopItemPosition < curTopItemPosition) {
                lastTopItemPosition = curTopItemPosition;
                lastBottomItemPosition = curBottomItemPosition;
                gcView(firstView);
                firstView = recyclerView.getChildAt(0);
                lastView = recyclerView.getChildAt(visibleCount - 1);
            } else if (lastBottomItemPosition > curBottomItemPosition) {
                lastTopItemPosition = curTopItemPosition;
                lastBottomItemPosition = curBottomItemPosition;
                gcView(lastView);
                firstView = recyclerView.getChildAt(0);
                lastView = recyclerView.getChildAt(visibleCount - 1);
            }
        }
    }

    private void gcView(View view) {
        if (view != null && view.findViewById(R.id.video) != null) {
            JCVideoPlayerStandard player = view.findViewById(R.id.video);
            if (player.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING
                    || player.currentState == JCVideoPlayer.CURRENT_STATE_ERROR) {
                player.setUiWitStateAndScreen(JCVideoPlayer.CURRENT_STATE_NORMAL);
                JCVideoPlayer.releaseAllVideos();
            }

        }
    }
}