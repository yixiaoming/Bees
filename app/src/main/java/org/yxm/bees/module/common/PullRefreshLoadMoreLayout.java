package org.yxm.bees.module.common;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.yxm.bees.R;


public class PullRefreshLoadMoreLayout extends ViewGroup {
    private static final String TAG = "PullRefreshLoadMore";

    public static final int STATE_RESET = -1;
    public static final int STATE_TRY_REFRESH = 0;
    public static final int STATE_TRY_LOADMORE = 1;
    public static final int STATE_REFRESHING = 2;
    public static final int STATE_LOADMOREING = 3;

    public static int ANIMATION_DURATION = 200;

    // 阻尼系数
    public static int SCROLL_RATE = 2;

    private View mHeader;
    private View mFooter;
    private View mTargetView;
    private TextView mHeaderStateText;
    private ImageView mHeaderLogo;
    private ProgressBar mHeaderProgress;

    private TextView mFooterStateText;

    private int mLastY;
    private int mState;
    private int dy;


    private IRefreshLoadmoreListener mListener;

    private IProcessPercentListener mPercentListener;

    public void setRefreshLoadmoreListener(IRefreshLoadmoreListener l) {
        this.mListener = l;
    }

    public void setPercentListener(IProcessPercentListener l) {
        this.mPercentListener = l;
    }

    public interface IRefreshLoadmoreListener {
        void onRefresh();

        void onLoadmore();
    }

    public interface IProcessPercentListener {
        void onRefreshPercent(float percent);

        void onLoadmorePercent(float percent);
    }

    public PullRefreshLoadMoreLayout(Context context) {
        this(context, null);
    }

    public PullRefreshLoadMoreLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullRefreshLoadMoreLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mHeader == null) {
            mHeader = LayoutInflater.from(getContext()).inflate(R.layout.refresh_header, this, false);
            addView(mHeader);
            mHeaderStateText = mHeader.findViewById(R.id.state_text);
            mHeaderLogo = mHeader.findViewById(R.id.state_logo);
            mHeaderProgress = mHeader.findViewById(R.id.state_progress);
        }
        if (mFooter == null) {
            mFooter = LayoutInflater.from(getContext()).inflate(R.layout.loadmore_footer, this, false);
            addView(mFooter);
            mFooterStateText = mFooter.findViewById(R.id.state_text);
        }
        mTargetView = getChildAt(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int mContentHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mHeader) {
                child.layout(0, 0 - child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
            } else if (child == mFooter) {
                child.layout(0, mContentHeight, child.getMeasuredWidth(), mContentHeight + child.getMeasuredHeight());
            } else {
                child.layout(0, mContentHeight, child.getMeasuredWidth(), mContentHeight + child.getMeasuredHeight());
                if (i < getChildCount()) {
                    mContentHeight += child.getMeasuredHeight();
                }
            }
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 向下滑动判断是否拦截下拉刷新
                if (y > mLastY) {
                    intercept = shouldInterceptRefresh(mTargetView);
                    if (intercept && mState == STATE_RESET) mState = STATE_TRY_REFRESH;
                }
                // 向上刮动判断拦截加载更多
                else {
                    intercept = shouldInterceptLoadmore(mTargetView);
                    if (intercept && mState == STATE_RESET) mState = STATE_TRY_LOADMORE;
                }
                break;
        }

        mLastY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        int scrollY = getScrollY();
        float percent;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                dy = y - mLastY;
                // 在下拉刷新过程中，方向滑动导致整体view上移
                if (scrollY > 0 && (mState == STATE_TRY_REFRESH || mState == STATE_REFRESHING)) {
                    scrollTo(0, 0);
                    mState = STATE_RESET;
                    // 释放touchevent
                    releaseTouchevent(ev);
                    break;
                }
                // 下拉过程中，方向方向导致view下移
                if (scrollY < 0 && (mState == STATE_TRY_LOADMORE || mState == STATE_LOADMOREING)) {
                    scrollTo(0, 0);
                    mState = STATE_RESET;
                    releaseTouchevent(ev);
                    break;
                }

                // 在下拉和正在刷新时都支持继续下拉，加载更多同理
                if (mState == STATE_TRY_REFRESH || mState == STATE_REFRESHING && scrollY <= 0) {
                    scrollBy(0, -dy / SCROLL_RATE);
                }
                if (mState == STATE_TRY_LOADMORE || mState == STATE_LOADMOREING && scrollY >= 0) {
                    scrollBy(0, -dy / SCROLL_RATE);
                }

                if (mState == STATE_TRY_REFRESH || mState == STATE_REFRESHING) {
                    percent = (float) (Math.abs(scrollY) * 1.0 / mHeader.getHeight());
                    percent = Math.min(1.0F, percent);
                    mHeaderLogo.setRotation(percent * 180 + 180);
                    if (percent >= 1.0) {
                        mHeaderStateText.setText(R.string.release_to_refresh);
                    } else {
                        mHeaderStateText.setText(R.string.pull_to_refresh);
                    }
                    if (mPercentListener != null) {
                        if (mState != STATE_REFRESHING) {
                            mPercentListener.onRefreshPercent(percent);
                        }
                    }
                } else if (mState == STATE_TRY_LOADMORE || mState == STATE_LOADMOREING) {
                    percent = (float) (Math.abs(scrollY) * 1.0 / mFooter.getHeight());
                    percent = Math.min(1.0F, percent);
                    if (percent >= 1.0) {
                        mFooterStateText.setText(R.string.release_to_loadmore);
                    } else {
                        mFooterStateText.setText(R.string.push_to_loadmore);
                    }
                    if (mPercentListener != null) {
                        mPercentListener.onLoadmorePercent(percent);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mState == STATE_TRY_REFRESH || mState == STATE_REFRESHING) {
                    if (Math.abs(scrollY) > mHeader.getHeight()) {
                        if (mState == STATE_TRY_REFRESH) {
                            if (mListener != null) {
                                mListener.onRefresh();
                            }
                        }
                        mHeaderStateText.setText(R.string.refreshing);
                        moveLayoutToHeaderHeight();
                    } else {
                        resetLayoutPosition();
                    }

                } else if (mState == STATE_TRY_LOADMORE || mState == STATE_LOADMOREING) {
                    if (Math.abs(scrollY) > mFooter.getHeight()) {
                        if (mState == STATE_TRY_LOADMORE) {
                            if (mListener != null) {
                                mListener.onLoadmore();
                            }
                        }
                        mFooterStateText.setText(R.string.loadmoreing);
                        moveLayoutToFooterHeight();
                    } else {
                        resetLayoutPosition();
                    }
                }
                else {
                    resetLayoutPosition();
                }
                break;
        }

        mLastY = y;
        return super.onTouchEvent(ev);
    }


    /**
     * 释放touchevent，让view重新接管
     * @param ev
     */
    private void releaseTouchevent(MotionEvent ev) {
        MotionEvent obtain = MotionEvent.obtain(ev);
        obtain.setAction(MotionEvent.ACTION_DOWN);
        this.dispatchTouchEvent(obtain);
    }

    /**
     * 回到header显示完全位置
     */
    private void moveLayoutToHeaderHeight() {
        mHeaderLogo.setVisibility(GONE);
        mHeaderProgress.setVisibility(VISIBLE);

        ObjectAnimator.ofInt(this, "scrollY", getScrollY(), -mHeader.getHeight()).setDuration(ANIMATION_DURATION).start();
        mState = STATE_REFRESHING;
    }

    /**
     * 回到footer显示完全位置
     */
    private void moveLayoutToFooterHeight() {
        ObjectAnimator.ofInt(this, "scrollY", getScrollY(), mFooter.getHeight()).setDuration(ANIMATION_DURATION).start();
        mState = STATE_LOADMOREING;
    }

    /**
     * view回到启始位置0
     */
    public void resetLayoutPosition() {
        mHeaderLogo.setVisibility(VISIBLE);
        mHeaderProgress.setVisibility(GONE);

        ObjectAnimator.ofInt(this, "scrollY", getScrollY(), 0).setDuration(ANIMATION_DURATION).start();
        mState = STATE_RESET;
    }

    public void setRefreshing(boolean inRefresh){
        if (inRefresh) {
            moveLayoutToHeaderHeight();
        }
        else{
            resetLayoutPosition();
        }
    }

    private boolean shouldInterceptRefresh(View child) {
        if (child instanceof RecyclerView) {
            return ((RecyclerView) child).computeVerticalScrollOffset() <= 0;
        }
        return false;
    }

    private boolean shouldInterceptLoadmore(View child) {
        if (child instanceof RecyclerView) {
            return ((RecyclerView) child).computeVerticalScrollExtent() + ((RecyclerView) child).computeVerticalScrollOffset()
                    >= ((RecyclerView) child).computeVerticalScrollRange();
        }
        return false;
    }
}
