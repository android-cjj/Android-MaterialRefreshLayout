package com.cjj;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;

public class MaterialRefreshLayout extends FrameLayout {

    public static final String Tag = "cjj_log";

    private MaterialHeadView materialHeadView;

    private boolean isOverlay;

    private int waveType;

    private int DEFAULT_WAVE_HEIGHT = 140;

    private int HIGHER_WAVE_HEIGHT = 180;

    private int DEFAULT_HEAD_HEIGHT = 70;

    private int hIGHER_HEAD_HEIGHT = 100;

    private int waveColor;

    protected float mWaveHeight;

    protected float mHeadHeight;

    private View mChildView;

    protected FrameLayout mHeadLayout;

    protected boolean isRefreshing;

    private float mTouchY;

    private float mCurrentY;

    private DecelerateInterpolator decelerateInterpolator;

    private MaterialHeadListener mMaterialHeadListener;

    private float headHeight;

    private float waveHeight;

    private int[] colorSchemeColors;

    private int colorsId;

    private int progressTextColor;

    private int progressValue,progressMax;

    private boolean showArrow;

    private int textType;

    private MaterialRefreshListener refreshListener;

    private boolean showProgressBg;

    private int progressBg;

    private boolean isShowWave;

    public MaterialRefreshLayout(Context context) {
        this(context, null, 0);
    }

    public MaterialRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defstyleAttr) {
        if (isInEditMode()) {
            return;
        }

        if (getChildCount() > 1) {
            throw new RuntimeException("can only have one child widget");
        }

        decelerateInterpolator = new DecelerateInterpolator(10);


        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.MaterialRefreshLayout, defstyleAttr, 0);
        isOverlay = t.getBoolean(R.styleable.MaterialRefreshLayout_overlay,false);
        /**attrs for materialWaveView*/
        waveType = t.getInt(R.styleable.MaterialRefreshLayout_wave_height_type, 0);
        if (waveType == 0) {
            headHeight = DEFAULT_HEAD_HEIGHT;
            waveHeight = DEFAULT_WAVE_HEIGHT;
            MaterialWaveView.DefaulHeadHeight = DEFAULT_HEAD_HEIGHT;
            MaterialWaveView.DefaulWaveHeight = DEFAULT_WAVE_HEIGHT;
        } else {
            headHeight = hIGHER_HEAD_HEIGHT;
            waveHeight = HIGHER_WAVE_HEIGHT;
            MaterialWaveView.DefaulHeadHeight = hIGHER_HEAD_HEIGHT;
            MaterialWaveView.DefaulWaveHeight = HIGHER_WAVE_HEIGHT;
        }
        waveColor = t.getColor(R.styleable.MaterialRefreshLayout_wave_color, Color.WHITE);
        isShowWave = t.getBoolean(R.styleable.MaterialRefreshLayout_wave_show,true);

        /**attrs for circleprogressbar*/
        colorsId = t.getResourceId(R.styleable.MaterialRefreshLayout_progress_colors, R.array.material_colors);
        colorSchemeColors = context.getResources().getIntArray(colorsId);
        showArrow = t.getBoolean(R.styleable.MaterialRefreshLayout_progress_show_arrow, true);
        textType = t.getInt(R.styleable.MaterialRefreshLayout_progress_text_visibility, 1);
        progressTextColor = t.getColor(R.styleable.MaterialRefreshLayout_progress_text_color, Color.BLACK);
        progressValue = t.getInteger(R.styleable.MaterialRefreshLayout_progress_value, 0);
        progressMax = t.getInteger(R.styleable.MaterialRefreshLayout_progress_max_value, 100);
        showProgressBg = t.getBoolean(R.styleable.MaterialRefreshLayout_progress_show_circle_backgroud, true);
        progressBg = t.getColor(R.styleable.MaterialRefreshLayout_progress_backgroud_color,CircleProgressBar.DEFAULT_CIRCLE_BG_LIGHT);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        FrameLayout headViewLayout = new FrameLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.gravity = Gravity.TOP;
        headViewLayout.setLayoutParams(layoutParams);

        mHeadLayout = headViewLayout;

        this.addView(mHeadLayout);

        mChildView = getChildAt(0);

        if (mChildView == null) {
            return;
        }

        setWaveHeight(Util.dip2px(getContext(), waveHeight));
        setHeaderHeight(Util.dip2px(getContext(), headHeight));

        materialHeadView = new MaterialHeadView(getContext());
        materialHeadView.setWaveColor(isShowWave ? waveColor : Color.WHITE);
        materialHeadView.showProgressArrow(showArrow);
        materialHeadView.setProgressColors(colorSchemeColors);
        materialHeadView.setProgressStokeWidth(3);
        materialHeadView.setTextType(textType);
        materialHeadView.setProgressTextColor(progressTextColor);
        materialHeadView.setProgressValue(progressValue);
        materialHeadView.setProgressValueMax(progressMax);
        materialHeadView.setIsProgressBg(showProgressBg);
        materialHeadView.setProgressBg(progressBg);
        setHeaderView(materialHeadView);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isRefreshing) return true;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = ev.getY();
                mCurrentY = mTouchY;
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = ev.getY();
                float dy = currentY - mTouchY;
                if (dy > 0 && !canChildScrollUp()) {
                    if (materialHeadView != null) {
                        materialHeadView.onBegin(this);
                    }
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (isRefreshing) {
            return super.onTouchEvent(e);
        }

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mCurrentY = e.getY();

                float dy = mCurrentY - mTouchY;
                dy = Math.min(mWaveHeight * 2, dy);
                dy = Math.max(0, dy);

                if (mChildView != null) {
                    float offsetY = decelerateInterpolator.getInterpolation(dy / mWaveHeight / 2) * dy / 2;
                    float fraction = offsetY / mHeadHeight;
                    mHeadLayout.getLayoutParams().height = (int) offsetY;
                    mHeadLayout.requestLayout();
                    if (null != mMaterialHeadListener) {
                        mMaterialHeadListener.onPull(this, fraction);
                    }
                    if (materialHeadView != null) {
                        materialHeadView.onPull(this, fraction);

                    }
                    if (!isOverlay)
                        ViewCompat.setTranslationY(mChildView, offsetY);

                }
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mChildView != null) {
                    if (isOverlay) {
                        if (mHeadLayout.getLayoutParams().height > mHeadHeight) {

                            updateListener();

                            mHeadLayout.getLayoutParams().height = (int) mHeadHeight;
                            mHeadLayout.requestLayout();

                        } else {
                            mHeadLayout.getLayoutParams().height = 0;
                            mHeadLayout.requestLayout();
                        }

                    } else {

                        if (ViewCompat.getTranslationY(mChildView) >= mHeadHeight) {
                            createAnimatorTranslationY(mChildView, mHeadHeight, mHeadLayout);
                           updateListener();
                        } else {
                            createAnimatorTranslationY(mChildView, 0, mHeadLayout);
                            if (null != mMaterialHeadListener) {

                            }
                        }


                    }

                }
                return true;
        }
        return super.onTouchEvent(e);
    }

    public void updateListener()
    {
        isRefreshing = true;
        /**
         * refreshing。。。
         */
        if (materialHeadView != null) {
            materialHeadView.onRefreshing(MaterialRefreshLayout.this);
        }

        if (refreshListener != null) {
            refreshListener.onRefresh(MaterialRefreshLayout.this);
        }

        if (null != mMaterialHeadListener) {
            mMaterialHeadListener.onRefreshing(this);
        }

    }

    public void setProgressColors(int[] colors)
    {
        this.colorSchemeColors = colors;
    }

    public void setShowArrow(boolean showArrow)
    {
        this.showArrow = showArrow;
    }

    public void setShowProgressBg(boolean showProgressBg)
    {
        this.showProgressBg = showProgressBg;
    }

    public void setWaveColor(int waveColor)
    {
        this.waveColor = waveColor;
    }

    public void setWaveShow(boolean isShowWave){
        this.isShowWave = isShowWave;
    }

    public void setIsOverLay(boolean isOverLay)
    {
        this.isOverlay = isOverLay;
    }

    public void setProgressValue(int progressValue)
    {
        this.progressValue = progressValue;
        materialHeadView.setProgressValue(progressValue);
    }

    public void createAnimatorTranslationY(final View v, final float h, final FrameLayout fl) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, "translationY", ViewCompat.getTranslationY(v), h);
        objectAnimator.setDuration(200);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float height = ViewCompat.getTranslationY(v);
                fl.getLayoutParams().height = (int) height;
                fl.requestLayout();
            }
        });
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    public boolean canChildScrollUp() {
        if (mChildView == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mChildView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mChildView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mChildView, -1) || mChildView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mChildView, -1);
        }
    }

    public void setWaveHigher() {
        headHeight = hIGHER_HEAD_HEIGHT;
        waveHeight = HIGHER_WAVE_HEIGHT;
        MaterialWaveView.DefaulHeadHeight = hIGHER_HEAD_HEIGHT;
        MaterialWaveView.DefaulWaveHeight = HIGHER_WAVE_HEIGHT;
    }

    public void finishRefreshing() {
        if (mChildView != null) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mChildView, "translationY", ViewCompat.getTranslationY(mChildView), 0);
            objectAnimator.setDuration(200);
            objectAnimator.setInterpolator(new DecelerateInterpolator());
            objectAnimator.start();
            if (mMaterialHeadListener != null) {
                mMaterialHeadListener.onComlete(this);
            }

            if (materialHeadView != null) {
                materialHeadView.onComlete(MaterialRefreshLayout.this);
            }

            if (refreshListener != null) {
                refreshListener.onfinish();
            }
        }
        isRefreshing = false;
        progressValue = 0;
       setProgressValue(0);
    }

    public void finishRefresh()
    {
        this.post(new Runnable() {
            @Override
            public void run() {
                finishRefreshing();
            }
        });
    }

    public void setHeaderView(final View headerView) {
        post(new Runnable() {
            @Override
            public void run() {
                mHeadLayout.addView(headerView);
            }
        });
    }

    public void setWaveHeight(float waveHeight) {
        this.mWaveHeight = waveHeight;
    }

    public void setHeaderHeight(float headHeight) {
        this.mHeadHeight = headHeight;
    }


    public void setBRHeadListener(MaterialHeadListener materialHeadListener) {
        this.mMaterialHeadListener = materialHeadListener;
    }

    public void setMaterialRefreshListener(MaterialRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

}
