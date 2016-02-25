package com.cjj;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

public class MaterialWaveView extends View implements MaterialHeadListener {
    private int waveHeight;
    private int headHeight;
    public static int DefaulWaveHeight;
    public static int DefaulHeadHeight;
    private Path path;
    private Paint paint;
    private int color;

    public MaterialWaveView(Context context) {
        this(context, null, 0);
    }

    public MaterialWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public int getHeadHeight() {
        return headHeight;
    }

    public void setHeadHeight(int headHeight) {
        this.headHeight = headHeight;
    }

    public int getWaveHeight() {
        return waveHeight;
    }

    public void setWaveHeight(int waveHeight) {
        this.waveHeight = waveHeight;
    }

    public int getDefaulWaveHeight() {
        return DefaulWaveHeight;
    }

    public void setDefaulWaveHeight(int defaulWaveHeight) {
        DefaulWaveHeight = defaulWaveHeight;
    }

    public int getDefaulHeadHeight() {
        return DefaulHeadHeight;
    }

    public void setDefaulHeadHeight(int defaulHeadHeight) {
        DefaulHeadHeight = defaulHeadHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        paint.setColor(color);
        path.lineTo(0, headHeight);
        path.quadTo(getMeasuredWidth() / 2, headHeight + waveHeight, getMeasuredWidth(), headHeight);
        path.lineTo(getMeasuredWidth(), 0);
        canvas.drawPath(path, paint);
    }


    @Override
    public void onComlete(MaterialRefreshLayout br) {
        waveHeight = 0;
        ValueAnimator animator =ValueAnimator.ofInt(headHeight,0);
        animator.setDuration(200);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                headHeight = value;
                invalidate();
            }
        });
    }

    @Override
    public void onBegin(MaterialRefreshLayout br) {

    }

    @Override
    public void onPull(MaterialRefreshLayout br, float fraction) {
        setHeadHeight((int) (Util.dip2px(getContext(), DefaulHeadHeight) * Util.limitValue(1, fraction)));
        setWaveHeight((int) (Util.dip2px(getContext(), DefaulWaveHeight) * Math.max(0, fraction - 1)));
        invalidate();
    }

    @Override
    public void onRelease(MaterialRefreshLayout br, float fraction) {

    }

    @Override
    public void onRefreshing(MaterialRefreshLayout br) {
        setHeadHeight((int) (Util.dip2px(getContext(), DefaulHeadHeight)));
        ValueAnimator animator = ValueAnimator.ofInt(getWaveHeight(),0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i("anim", "value--->" + (int) animation.getAnimatedValue());
                setWaveHeight((int) animation.getAnimatedValue());
                invalidate();
            }
        });
        animator.setInterpolator(new BounceInterpolator());
        animator.setDuration(200);
        animator.start();
    }



}
