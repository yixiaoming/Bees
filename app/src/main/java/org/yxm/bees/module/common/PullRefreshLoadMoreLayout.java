package org.yxm.bees.module.common;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yxm.bees.R;

public class PullRefreshLoadMoreLayout extends ViewGroup {
    private static final String TAG = "PullRefreshLoadMore";

    private View mHeader;
    private View mFooter;

    private TextView mContentText;

    private int mContentHeight;

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
        void onRefreshPercent(int percent);

        void onLoadmorePercent(int percent);
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
            mHeader = LayoutInflater.from(getContext()).inflate(R.layout.loadmore_footer, this, false);
            addView(mHeader);
            mContentText = mHeader.findViewById(R.id.loadmore_text);

        }
        if (mFooter == null) {
            mFooter = LayoutInflater.from(getContext()).inflate(R.layout.loadmore_footer, this, false);
            addView(mFooter);
            mContentText = mHeader.findViewById(R.id.loadmore_text);

        }
        child0 = getChildAt(0);
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
        mContentHeight = 0;
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

    private View child0;
    private int mLastInterceptY;
    private int mState;
    private int dy;

    public static final int STATE_RESET = -1;
    public static final int STATE_TRY_REFRESH = 0;
    public static final int STATE_TRY_LOADMORE = 1;
    public static final int STATE_REFRESHING = 3;
    public static final int STATE_LOADMOREING = 4;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int y = (int) ev.getY();

        if (mState == STATE_REFRESHING || mState == STATE_LOADMOREING) {
            return false;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastInterceptY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (y > mLastInterceptY) {
                    intercept = shouldInterceptRefresh(child0);
                    if (intercept) mState = STATE_TRY_REFRESH;
                } else {
                    intercept = shouldInterceptLoadmore(child0);
                    if (intercept) mState = STATE_TRY_LOADMORE;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        mLastInterceptY = y;

        return intercept;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();

        int scrollY = getScrollY();
        float percent;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                dy = y - mLastInterceptY;
                Log.e(TAG, "onTouchEvent: state:" + mState + " dy:" + dy + " scrollY:" + scrollY);
                if (mState == STATE_TRY_REFRESH && scrollY <= 0) {
                    scrollBy(0, -dy);
                    Log.e(TAG, "1scroll:" + dy);
                }
                if (mState == STATE_TRY_LOADMORE && scrollY >= 0) {
                    scrollBy(0, -dy);
                    Log.e(TAG, "2scroll:" + dy);
                }

                if (mState == STATE_TRY_REFRESH) {
                    percent = (float) (Math.abs(scrollY) * 1.0 / mHeader.getHeight());
                    percent = percent > 1.0 ? (float) 1.0 : percent;
                    if (percent >= 1.0) {
                        mContentText.setText("松开开始刷新");
                    } else {
                        mContentText.setText("继续下拉");
                    }
                    if (mPercentListener != null) {
                        mPercentListener.onRefreshPercent((int) (percent * 100));
                    }
                } else if (mState == STATE_TRY_LOADMORE) {
                    percent = (float) (Math.abs(scrollY) * 1.0 / mFooter.getHeight());
                    percent = percent > 1.0 ? (float) 1.0 : percent;
                    if (percent >= 1.0) {
                        mContentText.setText("松开加载更多");
                    } else {
                        mContentText.setText("继续上拉");
                    }
                    if (mPercentListener != null) {
                        mPercentListener.onLoadmorePercent((int) (percent * 100));
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mState == STATE_TRY_REFRESH) {
                    if (Math.abs(scrollY) > mHeader.getHeight()) {
                        if (mListener != null) {
                            mListener.onRefresh();
                            mContentText.setText("正在刷新");
                        }
                        moveLayoutToHeaderHeight();
                    } else {
                        resetLayoutPosition();
                    }

                } else if (mState == STATE_TRY_LOADMORE) {
                    if (Math.abs(scrollY) > mFooter.getHeight()) {
                        if (mListener != null) {
                            mListener.onLoadmore();
                            mContentText.setText("正在加载更多");
                        }
                        moveLayoutToFooterHeight();
                    } else {
                        resetLayoutPosition();
                    }
                } else {
                    resetLayoutPosition();
                }
                break;
        }

        mLastInterceptY = y;
        return super.onTouchEvent(ev);
    }


    /**
     * 回到header显示完全位置
     */
    private void moveLayoutToHeaderHeight() {
        ObjectAnimator.ofInt(this, "scrollY", getScrollY(), -mHeader.getHeight()).setDuration(100).start();
        mState = STATE_REFRESHING;
    }

    /**
     * 回到footer显示完全位置
     */
    private void moveLayoutToFooterHeight() {
        ObjectAnimator.ofInt(this, "scrollY", getScrollY(), mFooter.getHeight()).setDuration(100).start();
        mState = STATE_LOADMOREING;
    }

    /**
     * view回到启始位置0
     */
    public void resetLayoutPosition() {
        ObjectAnimator.ofInt(this, "scrollY", getScrollY(), 0).setDuration(300).start();
        mState = STATE_RESET;
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
