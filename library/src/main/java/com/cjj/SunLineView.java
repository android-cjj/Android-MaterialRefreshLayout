package com.cjj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by cjj on 2016/2/22.
 */
public class SunLineView extends View {

    private static final String Tag = SunLineView.class.getSimpleName();

    private int mHeight, mWidth;
    private Paint mLinePaint;//线的画笔
    private int mLineLeft, mLineTop;// 线的左、上位置
    private int mLineHeight;  // 短线长度
    private int mLineWidth;//线宽度
    private int mFixLineHeight;
    private int mLineBottom;
    private int mSunRadius;
    private Rect debugRect;
    private RectF mouthRect;
    private DrawFilter mDrawFilter;
    private int mLineColor;
    private int mLineLevel;

    public SunLineView(Context context) {
        this(context, null);
    }

    public SunLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SunLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.i(Tag, "init");

        mLineWidth = changeDp(1);
        mLineHeight = changeDp(3);
        mFixLineHeight = changeDp(6);
        mSunRadius = changeDp(12);
        mLineColor = Color.RED;
        mLineLevel = 30;

        //线的配置
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        // 设置画笔宽度
        mLinePaint.setStrokeWidth(mLineWidth);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG);
        debugRect = new Rect();
        mouthRect = new RectF();
    }

    public void setLineColor(int lineColor){
        mLineColor = lineColor;
        invalidate();
    }

    public void setLineWidth(int lineWidth){
        mLineWidth = changeDp(lineWidth);
        invalidate();
    }

    public void setLineHeight(int lineHeight){
        mLineHeight = changeDp(lineHeight);
        mFixLineHeight = mLineHeight*2;
        invalidate();
    }

    /**
     * 设置太阳半径
     *
     * @param sunRadius
     */
    public void setSunRadius(int sunRadius) {
        mSunRadius = changeDp(sunRadius);
        invalidate();
    }

    public void setLineLevel(int level){
        mLineLevel = level;
        invalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(Tag, "w---->" + w + "  -------  h----->" + h);
        mWidth = w;
        mHeight = h;

        mLineLeft = mWidth / 2 - mLineWidth / 2;
        mLineTop = h / 2 - mSunRadius - mFixLineHeight;
        mLineBottom = mLineTop + mLineHeight;

        debugRect.left = mWidth / 2 - mSunRadius - mFixLineHeight;
        debugRect.right = mWidth / 2 + mSunRadius + mFixLineHeight;
        debugRect.top = mHeight / 2 - mSunRadius - mFixLineHeight;
        debugRect.bottom = mHeight / 2 + mSunRadius + mFixLineHeight;


        mouthRect.left = mWidth / 2 - mSunRadius / 2;
        mouthRect.right = mWidth / 2 + mSunRadius / 2;
        mouthRect.top = mHeight / 2 - mSunRadius / 2;
        mouthRect.bottom = mHeight / 2 + mSunRadius / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(Tag, "onMeasure");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = (mSunRadius + mFixLineHeight + mLineHeight) * 2 + getPaddingRight() + getPaddingLeft();
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {

            height = (mSunRadius + mFixLineHeight + mLineHeight) * 2 + getPaddingTop() + getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(mDrawFilter);
        super.onDraw(canvas);
        drawLines(canvas);
    }

    /**
     * 绘制line
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        for (int i = 0; i <= 360; i++) {
            if (i % mLineLevel == 0) {
                canvas.save();
                canvas.rotate(i, mWidth / 2, mHeight / 2);
                canvas.drawLine(mLineLeft, mLineTop, mLineLeft, mLineBottom, mLinePaint);
                canvas.restore();
            }
        }
    }

    public int changeDp(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                getResources().getDisplayMetrics());
    }
}
