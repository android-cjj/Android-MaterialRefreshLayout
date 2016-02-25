package com.cjj;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

/**
 * Created by cjj on 2016/2/22.
 */
public class SunLayout extends FrameLayout implements MaterialHeadListener {

    private final static String Tag = SunLayout.class.getSimpleName();
    protected static final int DEFAULT_SUN_RADIUS = 12;//太阳的半径
    private static final int DEFAULT_SUN_COLOR = Color.RED;
    private static final int DEFAULT_SUN_EYES_SIZE = 2;
    private static final int DEFAULT_LINE_HEIGHT = 3;
    private static final int DEFAULT_LINE_WIDTH = 1;
    private static final int DEFAULT_LINE_LEVEL = 30;
    private static final int DEFAULT_MOUTH_WIDTH = 3;
    private static final int DEFAULT_LINE_COLOR = Color.RED;

    protected SunFaceView mSunView;
    protected SunLineView mLineView;
    private int mSunRadius;
    private int mSunColor;
    private int mEyesSize;
    private int mLineLevel;
    private int mMouthStro;
    private int mLineColor, mLineWidth, mLineHeight;

    private ObjectAnimator mAnimator;

    public SunLayout(Context context) {
        this(context, null);
    }

    public SunLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        /**
         * 这里偷懒了，如果你想在xml配置太阳的各个属性，可以在这里设置，然后传给SunFaceView和SunLineView就可以了
         */
        mSunRadius = DEFAULT_SUN_RADIUS;
        mSunColor = DEFAULT_SUN_COLOR;
        mEyesSize = DEFAULT_SUN_EYES_SIZE;
        mLineColor = DEFAULT_LINE_COLOR;
        mLineHeight = DEFAULT_LINE_HEIGHT;
        mLineWidth = DEFAULT_LINE_WIDTH;
        mLineLevel = DEFAULT_LINE_LEVEL;
        mMouthStro = DEFAULT_MOUTH_WIDTH;

        Context context = getContext();
        mSunView = new SunFaceView(context);
        mSunView.setSunRadius(mSunRadius);
        mSunView.setSunColor(mSunColor);
        mSunView.setEyesSize(mEyesSize);
        mSunView.setMouthStro(mMouthStro);
        addView(mSunView);

        mLineView = new SunLineView(context);
        mLineView.setSunRadius(mSunRadius);
        mLineView.setLineLevel(mLineLevel);
        mLineView.setLineColor(mLineColor);
        mLineView.setLineHeight(mLineHeight);
        mLineView.setLineWidth(mLineWidth);
        addView(mLineView);

        startSunLineAnim(mLineView);
    }

    /**
     * 设置太阳半径
     *
     * @param sunRadius
     */
    public void setSunRadius(int sunRadius) {
        mSunRadius = sunRadius;
        mSunView.setSunRadius(mSunRadius);
        mLineView.setSunRadius(mSunRadius);
    }

    /**
     * 设置太阳颜色
     *
     * @param sunColor
     */
    public void setSunColor(int sunColor) {
        mSunColor = sunColor;
        mSunView.setSunColor(mSunColor);
    }

    /**
     * 设置太阳眼睛大小
     *
     * @param eyesSize
     */
    public void setEyesSize(int eyesSize) {
        mEyesSize = eyesSize;
        mSunView.setEyesSize(mEyesSize);
    }

    /**
     * 设置太阳线的数量等级
     *
     * @param level
     */
    public void setLineLevel(int level) {
        mLineLevel = level;
        mLineView.setLineLevel(mLineLevel);
    }

    /**
     * 设置太阳线的颜色
     *
     * @param lineColor
     */
    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
        mLineView.setLineColor(mLineColor);
    }

    /**
     * 设置太阳线的宽度
     *
     * @param lineWidth
     */
    public void setLineWidth(int lineWidth) {
        mLineWidth = lineWidth;
        mLineView.setLineWidth(mLineWidth);
    }

    /**
     * 设置太阳线的长度
     *
     * @param lineHeight
     */
    public void setLineHeight(int lineHeight) {
        mLineHeight = lineHeight;
        mLineView.setLineHeight(mLineHeight);
    }

    /**
     * 设置嘴巴粗细
     *
     * @param mouthStro
     */
    public void setMouthStro(int mouthStro) {
        mMouthStro = mouthStro;
        mSunView.setMouthStro(mMouthStro);
    }


    /**
     * 开启转圈圈
     *
     * @param v
     */
    public void startSunLineAnim(View v) {
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(v, "rotation", 0f, 720f);
            mAnimator.setDuration(7 * 1000);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        }
        if (!mAnimator.isRunning())
            mAnimator.start();
    }

    /**
     * 停止动画
     */
    public void cancelSunLineAnim() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }

    @Override
    public void onComlete(MaterialRefreshLayout materialRefreshLayout) {
        cancelSunLineAnim();
        ViewCompat.setScaleX(this, 0);
        ViewCompat.setScaleY(this, 0);
    }

    @Override
    public void onBegin(MaterialRefreshLayout materialRefreshLayout) {

        ViewCompat.setScaleX(this, 0.001f);
        ViewCompat.setScaleY(this, 0.001f);
    }

    @Override
    public void onPull(MaterialRefreshLayout materialRefreshLayout, float fraction) {
        float a = Util.limitValue(1, fraction);
        if (a >= 0.7) {
            mLineView.setVisibility(View.VISIBLE);
        } else {
            mLineView.setVisibility(View.GONE);
        }
        mSunView.setPerView(mSunRadius, a);
        ViewCompat.setScaleX(this, a);
        ViewCompat.setScaleY(this, a);
        ViewCompat.setAlpha(this, a);
    }

    @Override
    public void onRelease(MaterialRefreshLayout materialRefreshLayout, float fraction) {

    }

    @Override
    public void onRefreshing(MaterialRefreshLayout materialRefreshLayout) {
        startSunLineAnim(mLineView);
    }
}
