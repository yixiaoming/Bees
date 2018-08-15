package org.yxm.bees.module.common;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yxm on 2018.7.28.
 */

public class SwipeCloseActivity extends AppCompatActivity {

    private static final String TAG = "SwipeCloseActivity";
    protected View mDecorView;
    protected int screenWidth;

    protected float mStartX;
    protected float mStartY;
    protected float mEndX;
    protected float mEndY;
    protected float mDistanceX;
    protected float mDistanceY;

    protected MoveStatus mMoveFlag = MoveStatus.END_MOVE;

    enum MoveStatus {
        START_MOVE,
        END_MOVE
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDecorView = getWindow().getDecorView();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = ev.getX();
                mStartY = ev.getY();
                if (mStartX < screenWidth / 4) {
                    mMoveFlag = MoveStatus.START_MOVE;
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mEndX = ev.getX();
                mEndY = ev.getY();
                mDistanceX = mEndX - mStartX;
                mDistanceY = mEndY - mStartY;
                if (mMoveFlag != MoveStatus.START_MOVE) {
                    break;
                }
                if (mDistanceX > 0
                        && (Math.abs(mDistanceX) > Math.abs(mDistanceY))) {
                    mDecorView.setX(mDistanceX);
                }
                return false;
            case MotionEvent.ACTION_UP:
                mEndX = ev.getX();
                mEndY = ev.getY();
                mDistanceX = mEndX - mStartX;
                mDistanceY = mEndY - mStartY;
                if (mMoveFlag != MoveStatus.START_MOVE) {
                    break;
                }
                if (mDistanceX > 0) {
                    if ((Math.abs(mDistanceX) > Math.abs(mDistanceY)
                            && mDistanceX > screenWidth / 3)) {
                        decorViewMoveOut(mDistanceX);
                    } else {
                        decorViewBackToOrigin(mDistanceX);
                    }
                } else {
                    mDecorView.setX(0);
                }
                mMoveFlag = MoveStatus.END_MOVE;
                return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void decorViewBackToOrigin(float distanceX) {
        ObjectAnimator.ofFloat(mDecorView, "X", distanceX, 0).setDuration(300).start();
    }

    private void decorViewMoveOut(float distanceX) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(distanceX, screenWidth);
        valueAnimator.setDuration(300);
        valueAnimator.start();
        valueAnimator.addUpdateListener(animation -> mDecorView.setX((Float) animation.getAnimatedValue()));

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
