package org.yxm.lib.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.yxm.lib.view.R;

/**
 * 下拉刷新Layout
 * @author yixiaoming
 */
public class PullToRefreshLayout extends ViewGroup {

    private static final String TAG = "SwipeRefreshLayout";
    private static final long ANIMATION_DURATION = 200;

    private View mHeaderView;
    private TextView mHeaderText;
    private ViewGroup mAbsListView;

    private OnRefreshListener mOnRefreshListener;

    // 记录上一次touch的位置
    private int mLastTouchY = 0;
    // 记录当前header状态
    private Status mStatus;
    // 记录是否还在touch事件中，up时修改为false
    private boolean mIsTouch;
    // 正在刷新状态中
    private boolean isInRefreshing;


    public PullToRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public PullToRefreshLayout(@NonNull Context context, @NonNull AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(@NonNull Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAbsListView = (ViewGroup) getChildAt(0);
        if (mHeaderView == null) {
            mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh_header, this, false);
            addView(mHeaderView);
            mHeaderText = mHeaderView.findViewById(R.id.pull_to_refresh_header_text);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int contentHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mHeaderView) {
                child.layout(0, -child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
            } else {
                child.layout(0, contentHeight, child.getMeasuredWidth(), contentHeight + child.getMeasuredHeight());
                if (i < getChildCount()) {
                    contentHeight += child.getMeasuredHeight();
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent: " + ev.getAction() + ", " + ev.getY() + getScrollY());
        boolean shouldIntercept = false;
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastTouchY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 向下滑动
                if (y - mLastTouchY > 0) {
                    shouldIntercept = shouldIntercept(mAbsListView);
                }
                break;
        }
        mLastTouchY = y;
        return shouldIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onTouchEvent: " + ev.getAction() + ", " + ev.getY() + ", " + getScrollY());
        boolean consume = false;
        int y = (int) ev.getY();
        mIsTouch = true;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // 未释放，向上超出
                if (getScrollY() > 0) {
                    releaseTouchevent(ev);
                    break;
                }
                int dy = y - mLastTouchY;
                scrollBy(0, (int) (-dy * 0.5));
                updateHeaderStatus();
                break;

            case MotionEvent.ACTION_UP:
                mIsTouch = false;
                updateHeaderStatus();
                break;
        }
        mLastTouchY = y;
        return consume;
    }


    private enum Status {
        TRY_REFRESH() {
            @Override
            public void updateHeader() {
                Log.d(TAG, "updateHeader: " + TRY_REFRESH);
            }
        },
        RELESE_TO_REFRESH() {
            @Override
            public void updateHeader() {
                Log.d(TAG, "updateHeader: " + RELESE_TO_REFRESH);

            }
        },
        REFRESHING() {
            @Override
            public void updateHeader() {
                Log.d(TAG, "updateHeader: " + REFRESHING);
            }
        };

        public void updateHeader() {
            throw new RuntimeException("must override this method");
        }

    }

    /**
     * 更新header状态
     */
    private void updateHeaderStatus() {
        int scrollY = getScrollY();
        if (mIsTouch && -scrollY >= mHeaderView.getHeight()) {
            mStatus = Status.RELESE_TO_REFRESH;
        } else if (!mIsTouch && -scrollY >= mHeaderView.getHeight()) {
            mStatus = Status.REFRESHING;
        } else {
            mStatus = Status.TRY_REFRESH;
        }
        mStatus.updateHeader();

        switch (mStatus) {
            case REFRESHING:
                mHeaderText.setText("正在刷新");
                if (mOnRefreshListener != null && !isInRefreshing) {
                    mOnRefreshListener.OnRefreshing();
                }
                moveToHeaderHeight();
                break;
            case TRY_REFRESH:
                mHeaderText.setText("下拉刷新");
                if (!mIsTouch) {
                    moveToOriginHeight();
                }
                break;
            case RELESE_TO_REFRESH:
                mHeaderText.setText("松开开始刷新");
                break;
        }
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
    private void moveToHeaderHeight() {
        ObjectAnimator.ofInt(this, "scrollY", getScrollY(), -mHeaderView.getHeight()).setDuration(ANIMATION_DURATION).start();
        isInRefreshing = true;
    }

    /**
     * 回到默认位置
     */
    private void moveToOriginHeight() {
        ObjectAnimator.ofInt(this, "scrollY", getScrollY(), 0).setDuration(ANIMATION_DURATION).start();
        isInRefreshing = false;
    }

    /**
     * 外部接口，重制refresh状态
     */
    public void resetHeader() {
        moveToOriginHeight();
    }

    public void setPullToRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    /**
     * 刷新Listener
     */
    public interface OnRefreshListener {

        void OnRefreshing();

    }

    /**
     * 根据 AbsListView状态判断是否应该拦截事件
     * @return
     */
    private boolean shouldIntercept(ViewGroup absListView) {
        if (absListView instanceof ListView) {
            ListView listView = (ListView) absListView;
            return listView.getFirstVisiblePosition() == 0
                    && getScrollY() <= listView.getMeasuredHeight();
        } else if (absListView instanceof RecyclerView) {
            return canScrollVertically(1, absListView);
        }
        return false;
    }

    /**
     * 判断recyclerview是否可以上下移动，direction<0 表示向上， direction>0 表示向下
     * @param direction direction<0 表示向上， direction>0 表示向下
     * @return
     */
    private boolean canScrollVertically(int direction, View view) {
        return view.canScrollVertically(direction);
    }

}
