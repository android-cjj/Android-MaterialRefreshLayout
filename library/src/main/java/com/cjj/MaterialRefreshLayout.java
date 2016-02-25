package com.cjj;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;

public class MaterialRefreshLayout extends FrameLayout {

    public static final String Tag = MaterialRefreshLayout.class.getSimpleName();
    private final static int DEFAULT_WAVE_HEIGHT = 140;
    private final static int HIGHER_WAVE_HEIGHT = 180;
    private final static int DEFAULT_HEAD_HEIGHT = 70;
    private final static int hIGHER_HEAD_HEIGHT = 100;
    private final static int DEFAULT_PROGRESS_SIZE = 50;
    private final static int BIG_PROGRESS_SIZE = 60;
    private final static int PROGRESS_STOKE_WIDTH = 3;

    private MaterialHeaderView mMaterialHeaderView;
    private MaterialFooterView mMaterialFooterView;
    private SunLayout mSunLayout;
    private boolean isOverlay;
    private int waveType;
    private int waveColor;
    protected float mWaveHeight;
    protected float mHeadHeight;
    private View mChildView;
    protected boolean isRefreshing;
    private float mTouchY;
    private float mCurrentY;
    private DecelerateInterpolator decelerateInterpolator;
    private float headHeight;
    private float waveHeight;
    private int[] colorSchemeColors;
    private int colorsId;
    private int progressTextColor;
    private int progressValue, progressMax;
    private boolean showArrow = true;
    private int textType;
    private MaterialRefreshListener refreshListener;
    private boolean showProgressBg;
    private int progressBg;
    private boolean isShowWave;
    private int progressSizeType;
    private int progressSize = 0;
    private boolean isLoadMoreing;
    private boolean isLoadMore;
    private boolean isSunStyle = false;

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
        isOverlay = t.getBoolean(R.styleable.MaterialRefreshLayout_overlay, false);
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
        isShowWave = t.getBoolean(R.styleable.MaterialRefreshLayout_wave_show, true);

        /**attrs for circleprogressbar*/
        colorsId = t.getResourceId(R.styleable.MaterialRefreshLayout_progress_colors, R.array.material_colors);
        colorSchemeColors = context.getResources().getIntArray(colorsId);
        showArrow = t.getBoolean(R.styleable.MaterialRefreshLayout_progress_show_arrow, true);
        textType = t.getInt(R.styleable.MaterialRefreshLayout_progress_text_visibility, 1);
        progressTextColor = t.getColor(R.styleable.MaterialRefreshLayout_progress_text_color, Color.BLACK);
        progressValue = t.getInteger(R.styleable.MaterialRefreshLayout_progress_value, 0);
        progressMax = t.getInteger(R.styleable.MaterialRefreshLayout_progress_max_value, 100);
        showProgressBg = t.getBoolean(R.styleable.MaterialRefreshLayout_progress_show_circle_backgroud, true);
        progressBg = t.getColor(R.styleable.MaterialRefreshLayout_progress_backgroud_color, CircleProgressBar.DEFAULT_CIRCLE_BG_LIGHT);
        progressSizeType = t.getInt(R.styleable.MaterialRefreshLayout_progress_size_type, 0);
        if (progressSizeType == 0) {
            progressSize = DEFAULT_PROGRESS_SIZE;
        } else {
            progressSize = BIG_PROGRESS_SIZE;
        }
        isLoadMore = t.getBoolean(R.styleable.MaterialRefreshLayout_isLoadMore, false);
        t.recycle();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(Tag, "onAttachedToWindow");

        Context context = getContext();

        mChildView = getChildAt(0);

        if (mChildView == null) {
            return;
        }

        setWaveHeight(Util.dip2px(context, waveHeight));
        setHeaderHeight(Util.dip2px(context, headHeight));

        if (isSunStyle) {
            mSunLayout = new SunLayout(context);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dip2px(context, hIGHER_HEAD_HEIGHT));
            layoutParams.gravity = Gravity.TOP;
            mSunLayout.setVisibility(View.GONE);
            setHeaderView(mSunLayout);
        } else {
            mMaterialHeaderView = new MaterialHeaderView(context);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dip2px(context, hIGHER_HEAD_HEIGHT));
            layoutParams.gravity = Gravity.TOP;
            mMaterialHeaderView.setLayoutParams(layoutParams);
            mMaterialHeaderView.setWaveColor(isShowWave ? waveColor : Color.TRANSPARENT);
            mMaterialHeaderView.showProgressArrow(showArrow);
            mMaterialHeaderView.setProgressSize(progressSize);
            mMaterialHeaderView.setProgressColors(colorSchemeColors);
            mMaterialHeaderView.setProgressStokeWidth(PROGRESS_STOKE_WIDTH);
            mMaterialHeaderView.setTextType(textType);
            mMaterialHeaderView.setProgressTextColor(progressTextColor);
            mMaterialHeaderView.setProgressValue(progressValue);
            mMaterialHeaderView.setProgressValueMax(progressMax);
            mMaterialHeaderView.setIsProgressBg(showProgressBg);
            mMaterialHeaderView.setProgressBg(progressBg);
            mMaterialHeaderView.setVisibility(View.GONE);
            setHeaderView(mMaterialHeaderView);
        }


        mMaterialFooterView = new MaterialFooterView(context);
        LayoutParams layoutParams2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Util.dip2px(context, hIGHER_HEAD_HEIGHT));
        layoutParams2.gravity = Gravity.BOTTOM;
        mMaterialFooterView.setLayoutParams(layoutParams2);
        mMaterialFooterView.showProgressArrow(showArrow);
        mMaterialFooterView.setProgressSize(progressSize);
        mMaterialFooterView.setProgressColors(colorSchemeColors);
        mMaterialFooterView.setProgressStokeWidth(PROGRESS_STOKE_WIDTH);
        mMaterialFooterView.setTextType(textType);
        mMaterialFooterView.setProgressValue(progressValue);
        mMaterialFooterView.setProgressValueMax(progressMax);
        mMaterialFooterView.setIsProgressBg(showProgressBg);
        mMaterialFooterView.setProgressBg(progressBg);
        mMaterialFooterView.setVisibility(View.GONE);
        setFooderView(mMaterialFooterView);
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
                    if (mMaterialHeaderView != null) {
                        mMaterialHeaderView.setVisibility(View.VISIBLE);
                        mMaterialHeaderView.onBegin(this);
                    } else if (mSunLayout != null) {
                        mSunLayout.setVisibility(View.VISIBLE);
                        mSunLayout.onBegin(this);
                    }
                    return true;
                } else if (dy < 0 && !canChildScrollDown() && isLoadMore) {
                    if (mMaterialFooterView != null && !isLoadMoreing) {
                        soveLoadMoreLogic();
                    }
                    return super.onInterceptTouchEvent(ev);
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void soveLoadMoreLogic() {
        isLoadMoreing = true;
        mMaterialFooterView.setVisibility(View.VISIBLE);
        mMaterialFooterView.onBegin(this);
        mMaterialFooterView.onRefreshing(this);
        if (refreshListener != null) {
            refreshListener.onRefreshLoadMore(MaterialRefreshLayout.this);
        }
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
                    if (mMaterialHeaderView != null) {
                        mMaterialHeaderView.getLayoutParams().height = (int) offsetY;
                        mMaterialHeaderView.requestLayout();
                        mMaterialHeaderView.onPull(this, fraction);
                    } else if (mSunLayout != null) {
                        mSunLayout.getLayoutParams().height = (int) offsetY;
                        mSunLayout.requestLayout();
                        mSunLayout.onPull(this, fraction);
                    }
                    if (!isOverlay)
                        ViewCompat.setTranslationY(mChildView, offsetY);

                }
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mChildView != null) {
                    if (mMaterialHeaderView != null) {
                        if (isOverlay) {
                            if (mMaterialHeaderView.getLayoutParams().height > mHeadHeight) {

                                updateListener();

                                mMaterialHeaderView.getLayoutParams().height = (int) mHeadHeight;
                                mMaterialHeaderView.requestLayout();

                            } else {
                                mMaterialHeaderView.getLayoutParams().height = 0;
                                mMaterialHeaderView.requestLayout();
                            }

                        } else {
                            if (ViewCompat.getTranslationY(mChildView) >= mHeadHeight) {
                                createAnimatorTranslationY(mChildView, mHeadHeight, mMaterialHeaderView);
                                updateListener();
                            } else {
                                createAnimatorTranslationY(mChildView, 0, mMaterialHeaderView);
                            }
                        }
                    } else if (mSunLayout != null) {
                        if (isOverlay) {
                            if (mSunLayout.getLayoutParams().height > mHeadHeight) {

                                updateListener();

                                mSunLayout.getLayoutParams().height = (int) mHeadHeight;
                                mSunLayout.requestLayout();

                            } else {
                                mSunLayout.getLayoutParams().height = 0;
                                mSunLayout.requestLayout();
                            }

                        } else {
                            if (ViewCompat.getTranslationY(mChildView) >= mHeadHeight) {
                                createAnimatorTranslationY(mChildView, mHeadHeight, mSunLayout);
                                updateListener();
                            } else {
                                createAnimatorTranslationY(mChildView, 0, mSunLayout);
                            }
                        }
                    }


                }
                return true;
        }

        return super.

                onTouchEvent(e);

    }

    public void setSunStyle(boolean isSunStyle) {
        this.isSunStyle = isSunStyle;
    }

    public void autoRefresh() {
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isRefreshing) {
                    if (mMaterialHeaderView != null) {
                        mMaterialHeaderView.setVisibility(View.VISIBLE);

                        if (isOverlay) {
                            mMaterialHeaderView.getLayoutParams().height = (int) mHeadHeight;
                            mMaterialHeaderView.requestLayout();
                        } else {
                            createAnimatorTranslationY(mChildView, mHeadHeight, mMaterialHeaderView);
                        }
                    } else if (mSunLayout != null) {
                        mSunLayout.setVisibility(View.VISIBLE);
                        if (isOverlay) {
                            mSunLayout.getLayoutParams().height = (int) mHeadHeight;
                            mSunLayout.requestLayout();
                        } else {
                            createAnimatorTranslationY(mChildView, mHeadHeight, mSunLayout);
                        }
                    }

                    updateListener();


                }
            }
        }, 50);


    }

    public void autoRefreshLoadMore() {
        this.post(new Runnable() {
            @Override
            public void run() {
                if (isLoadMore) {
                    soveLoadMoreLogic();
                } else {
                    throw new RuntimeException("you must setLoadMore ture");
                }
            }
        });
    }

    public void updateListener() {
        isRefreshing = true;

        if (mMaterialHeaderView != null) {
            mMaterialHeaderView.onRefreshing(MaterialRefreshLayout.this);
        } else if (mSunLayout != null) {
            mSunLayout.onRefreshing(MaterialRefreshLayout.this);
        }

        if (refreshListener != null) {
            refreshListener.onRefresh(MaterialRefreshLayout.this);
        }

    }

    public void setLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    public void setProgressColors(int[] colors) {
        this.colorSchemeColors = colors;
    }

    public void setShowArrow(boolean showArrow) {
        this.showArrow = showArrow;
    }

    public void setShowProgressBg(boolean showProgressBg) {
        this.showProgressBg = showProgressBg;
    }

    public void setWaveColor(int waveColor) {
        this.waveColor = waveColor;
    }

    public void setWaveShow(boolean isShowWave) {
        this.isShowWave = isShowWave;
    }

    public void setIsOverLay(boolean isOverLay) {
        this.isOverlay = isOverLay;
    }

//    public void setProgressValue(int progressValue) {
//        this.progressValue = progressValue;
//        mMaterialHeaderView.setProgressValue(progressValue);
//    }

    public void createAnimatorTranslationY(final View v, final float h, final FrameLayout fl) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate(v);
        viewPropertyAnimatorCompat.setDuration(250);
        viewPropertyAnimatorCompat.setInterpolator(new DecelerateInterpolator());
        viewPropertyAnimatorCompat.translationY(h);
        viewPropertyAnimatorCompat.start();
        viewPropertyAnimatorCompat.setUpdateListener(new ViewPropertyAnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(View view) {
                float height = ViewCompat.getTranslationY(v);
                fl.getLayoutParams().height = (int) height;
                fl.requestLayout();
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

    public boolean canChildScrollDown() {
        if (mChildView == null) {
            return false;
        }
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mChildView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mChildView;
                if (absListView.getChildCount() > 0) {
                    int lastChildBottom = absListView.getChildAt(absListView.getChildCount() - 1).getBottom();
                    return absListView.getLastVisiblePosition() == absListView.getAdapter().getCount() - 1 && lastChildBottom <= absListView.getMeasuredHeight();
                } else {
                    return false;
                }

            } else {
                return ViewCompat.canScrollVertically(mChildView, 1) || mChildView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mChildView, 1);
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
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate(mChildView);
            viewPropertyAnimatorCompat.setDuration(200);
            viewPropertyAnimatorCompat.y(ViewCompat.getTranslationY(mChildView));
            viewPropertyAnimatorCompat.translationY(0);
            viewPropertyAnimatorCompat.setInterpolator(new DecelerateInterpolator());
            viewPropertyAnimatorCompat.start();

            if (mMaterialHeaderView != null) {
                mMaterialHeaderView.onComlete(MaterialRefreshLayout.this);
            } else if (mSunLayout != null) {
                mSunLayout.onComlete(MaterialRefreshLayout.this);
            }

            if (refreshListener != null) {
                refreshListener.onfinish();
            }
        }
        isRefreshing = false;
        progressValue = 0;
    }

    public void finishRefresh() {
        this.post(new Runnable() {
            @Override
            public void run() {
                finishRefreshing();
            }
        });
    }

    public void finishRefreshLoadMore() {
        this.post(new Runnable() {
            @Override
            public void run() {
                if (mMaterialFooterView != null && isLoadMoreing) {
                    isLoadMoreing = false;
                    mMaterialFooterView.onComlete(MaterialRefreshLayout.this);
                }
            }
        });

    }

    private void setHeaderView(final View headerView) {
        addView(headerView);
    }

    public void setHeader(final View headerView) {
        setHeaderView(headerView);
    }

    public void setFooderView(final View fooderView) {
        this.addView(fooderView);
    }


    public void setWaveHeight(float waveHeight) {
        this.mWaveHeight = waveHeight;
    }

    public void setHeaderHeight(float headHeight) {
        this.mHeadHeight = headHeight;
    }

    public void setMaterialRefreshListener(MaterialRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

}
