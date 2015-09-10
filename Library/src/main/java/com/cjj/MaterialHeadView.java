package com.cjj;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by cjj on 2015/9/7.
 */
public class MaterialHeadView extends FrameLayout implements MaterialHeadListener{
    private MaterialWaveView materialWaveView;
    private CircleProgressBar circleProgressBar;
    private int waveColor;
    private int progressTextColor;
    private int[] progress_colors;
    private int progressStokeWidth;
    private boolean isShowArrow,isShowProgressBg;
    private int progressValue,progressValueMax;
    private int textType;
    private int progressBg;
    private MaterialHeadListener listener;


    public MaterialHeadView(Context context) {
        this(context, null);
    }

    public MaterialHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialHeadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    protected void init(AttributeSet attrs, int defStyle) {
        if (isInEditMode()) return;
        setClipToPadding(false);
        setWillNotDraw(false);
    }

    public int getWaveColor() {
        return waveColor;
    }

    public void setWaveColor(int waveColor) {
        this.waveColor = waveColor;
        Log.i("cjj_log", "color-------123>" + waveColor);
    }

    public void setProgressBg(int progressBg)
    {
        this.progressBg = progressBg;
    }

    public void setIsProgressBg(boolean isShowProgressBg)
    {
        this.isShowProgressBg = isShowProgressBg;
    }

    public void setProgressTextColor(int textColor)
    {
        this.progressTextColor = textColor;
    }

    public void setProgressColors(int[] colors)
    {
        this.progress_colors = colors;
    }

    public void setTextType(int textType)
    {
        this.textType = textType;
    }

    public void setProgressValue(int value)
    {
        this.progressValue = value;
        Log.i("cjj_log", "progress------->>>" + progressValue);
        this.post(new Runnable() {
            @Override
            public void run() {
                circleProgressBar.setProgress(progressValue);
            }
        });

    }

    public void setProgressValueMax(int value)
    {
        this.progressValueMax = value;
    }

    public void setProgressStokeWidth(int w)
    {
        this.progressStokeWidth = w;
    }

    public void showProgressArrow(boolean isShowArrow)
    {
        this.isShowArrow = isShowArrow;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        materialWaveView = new MaterialWaveView(getContext());
        materialWaveView.setColor(waveColor);
        Log.i("cjj_log", "color-------1234>" + waveColor);
        addView(materialWaveView);

        circleProgressBar = new CircleProgressBar(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(Util.dip2px(getContext(),56),Util.dip2px(getContext(),56));
        layoutParams.gravity = Gravity.CENTER;
        circleProgressBar.setLayoutParams(layoutParams);
        circleProgressBar.setColorSchemeColors(progress_colors);
        circleProgressBar.setProgressStokeWidth(progressStokeWidth);
        circleProgressBar.setShowArrow(isShowArrow);
        circleProgressBar.setShowProgressText(textType == 0);
        circleProgressBar.setTextColor(progressTextColor);
        circleProgressBar.setProgress(progressValue);
        circleProgressBar.setMax(progressValueMax);
        circleProgressBar.setCircleBackgroundEnabled(isShowProgressBg);
        circleProgressBar.setProgressBackGroundColor(progressBg);
        addView(circleProgressBar);
    }

    @Override
    public void onComlete(MaterialRefreshLayout materialRefreshLayout) {
        if(materialWaveView != null)
        {
            materialWaveView.onComlete(materialRefreshLayout);
        }
        if(circleProgressBar != null)
        {
            circleProgressBar.onComlete(materialRefreshLayout);
            ViewHelper.setTranslationY(circleProgressBar,0);
            ViewHelper.setScaleX(circleProgressBar,0);
            ViewHelper.setScaleY(circleProgressBar,0);
        }


    }

    @Override
    public void onBegin(MaterialRefreshLayout materialRefreshLayout) {
        if(materialWaveView != null)
        {
            materialWaveView.onBegin(materialRefreshLayout);
        }
        if(circleProgressBar != null)
        {
            circleProgressBar.onBegin(materialRefreshLayout);
        }
    }

    @Override
    public void onPull(MaterialRefreshLayout materialRefreshLayout, float fraction) {
        if(materialWaveView != null)
        {
            materialWaveView.onPull(materialRefreshLayout, fraction);
        }
        if(circleProgressBar != null)
        {
            circleProgressBar.onPull(materialRefreshLayout, fraction);
            float a = Util.limitValue(1,fraction);
            ViewHelper.setScaleX(circleProgressBar, 1);
            ViewHelper.setScaleY(circleProgressBar, 1);
            ViewHelper.setAlpha(circleProgressBar, a);
        }
    }

    @Override
    public void onRelease(MaterialRefreshLayout materialRefreshLayout, float fraction) {

    }

    @Override
    public void onRefreshing(MaterialRefreshLayout materialRefreshLayout) {
        if(materialWaveView != null)
        {
            materialWaveView.onRefreshing(materialRefreshLayout);
        }
        if(circleProgressBar != null)
        {
            circleProgressBar.onRefreshing(materialRefreshLayout);
        }
    }



    public void scaleView(View v,float a,float b)
    {
        ObjectAnimator ax = ObjectAnimator.ofFloat(v,"scaleX",a,b);
        ObjectAnimator ay = ObjectAnimator.ofFloat(v,"scaleY",a,b);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(ax).with(ay);
        animSet.setDuration(200);
        animSet.start();
    }
}
