package com.simplewave.wave.library;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class WaveView extends View {

    private int mWidth = 0;
    private int mHeight = 0;
    private int mBaseLine = 100;
    private int mWaveHeight = 100;
    private int mWaveWidth;
    private float mOffset = 0f;
    ValueAnimator mAnimator;
    Paint mPaint;
    Paint mDarkPaint;

    public WaveView(Context context) {
        super(context);
        initView();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#98F5FF"));
        mPaint.setStyle(Paint.Style.FILL);
        mDarkPaint = new Paint();
        mDarkPaint.setColor(Color.BLUE);
        mDarkPaint.setStyle(Paint.Style.FILL);
    }

    public void setBaseLine(int baseLine){
        mBaseLine = baseLine;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(getWavePath(),mPaint);
        canvas.drawPath(getDarkWavePath(),mDarkPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mWaveWidth = mWidth;
        updateXControl();
    }

    private void updateXControl(){
        mAnimator = ValueAnimator.ofFloat(0,mWaveWidth);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                mOffset = value;
                postInvalidate();
            }
        });
        mAnimator.setDuration(2000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    private Path getWavePath(){
        int itemWidth = mWaveWidth/2;
        Path mPath = new Path();
        mPath.moveTo(-itemWidth * 3,mBaseLine);
        for(int i = -3;i<2;i++){
            int startX = i * itemWidth;
            mPath.quadTo(startX + mOffset + itemWidth/2,
                    getWaveHeight(i),
                    startX + mOffset + itemWidth,
                    mBaseLine);
        }
        mPath.lineTo(mWidth,mHeight);
        mPath.lineTo(0,mHeight);
        mPath.close();
        return mPath;
    }

    private Path getDarkWavePath(){
        int itemWidth = mWaveWidth/2;
        Path mPath = new Path();
        mPath.moveTo(itemWidth * 3,mBaseLine);
        for(int i = 3;i > -2;i--){
            int startX = i * itemWidth;
            mPath.quadTo(startX - mOffset + itemWidth,
                    getWaveHeight(i),
                    startX - mOffset + itemWidth/2,
                    mBaseLine);
        }
        mPath.lineTo(0,mHeight);
        mPath.lineTo(mWidth,mHeight);
        mPath.close();
        return mPath;
    }

    private int getWaveHeight(int pos){
        if(pos%2==0){
            return mBaseLine + mWaveHeight;
        } else {
            return mBaseLine - mWaveHeight;
        }
    }
}
