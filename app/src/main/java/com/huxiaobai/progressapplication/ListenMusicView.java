package com.huxiaobai.progressapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/2/8 11:57
 * 更新时间: 2022/2/8 11:57
 * 描述:
 */
public class ListenMusicView extends View {
    private static final int DEFAULT_SIZE = 50;
    private static final int DEFAULT_BORDER_WIDTH = 10;
    private int mDefaultSize = DEFAULT_SIZE;
    private int mCurrentProgress;
    private int mMaxProgress;
    private @ColorInt
    int mProgressColorInt;
    private @ColorInt
    int mAllColorInt;
    private int mBorderWidth;
    private Paint mAllPaint;
    private int mWidth;
    private int mHeight;
    private int mRadius;
    private Paint mProgressPaint;
    private static final int WHAT = 100;
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == WHAT) {
                if (mCurrentProgress >= mMaxProgress) {
                    pause();
                } else {
                    mCurrentProgress += 1;
                    invalidate();
                    start();
                }

            }
            return false;
        }
    });

    public ListenMusicView(Context context) {
        this(context, null);
    }

    public ListenMusicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ListenMusicView);
        if (typedArray != null) {
            mProgressColorInt = typedArray.getColor(R.styleable.ListenMusicView_lmv_progress_color, Color.BLACK);
            mAllColorInt = typedArray.getColor(R.styleable.ListenMusicView_lmv_all_color, Color.WHITE);
            mBorderWidth = typedArray.getDimensionPixelOffset(R.styleable.ListenMusicView_lmv_border_width, DEFAULT_BORDER_WIDTH);
            mDefaultSize = typedArray.getDimensionPixelOffset(R.styleable.ListenMusicView_lmv_default_size, DEFAULT_SIZE);
            mCurrentProgress = typedArray.getInt(R.styleable.ListenMusicView_lmv_current_progress, 0);
            mMaxProgress = typedArray.getInt(R.styleable.ListenMusicView_lmv_max_progress, 100);
            typedArray.recycle();
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initAllPaint();
        initProgressPaint();
    }

    private void initAllPaint() {
        mAllPaint = new Paint();
        mAllPaint.setDither(true);
        mAllPaint.setAntiAlias(true);
        mAllPaint.setStrokeCap(Paint.Cap.ROUND);
        mAllPaint.setColor(mAllColorInt);
        mAllPaint.setStyle(Paint.Style.STROKE);
        mAllPaint.setStrokeWidth(mBorderWidth);
    }

    private void initProgressPaint() {
        mProgressPaint = new Paint();
        mProgressPaint.setDither(true);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressPaint.setColor(mProgressColorInt);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mBorderWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasureSize(widthMeasureSpec);
        int height = getMeasureSize(heightMeasureSpec);
        if (width > height) {
            width = height;
        } else {
            height = width;
        }
        setMeasuredDimension(width, height);
        Log.w("ListenMusicView--", "onMeasure:" + width + "--" + height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = (mWidth - mBorderWidth) / 2;
        Log.w("ListenMusicView--", "onSizeChanged:" + w + "--" + h + "--" + oldw + "--" + oldh);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.w("ListenMusicView--", "draw:");
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mAllPaint);
        int sweepAngle = (int) ((float) mCurrentProgress / mMaxProgress * 360);
        canvas.drawArc(mBorderWidth / 2, mBorderWidth / 2, mWidth - mBorderWidth / 2,
                mHeight - mBorderWidth / 2, 270, sweepAngle, false, mProgressPaint);


    }

    private int getMeasureSize(int measureSpec) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            result = Math.min(size, mDefaultSize);
        } else if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = mDefaultSize;
        }
        return result;
    }

    public void setCurrentProgress(int progress) {
        mCurrentProgress = progress;
        Log.w("ListenMusicView--", "setProgress：");
    }

    public void setMaxProgress(int progress) {
        mMaxProgress = progress;
    }

    public void start() {
        mHandler.sendEmptyMessageDelayed(WHAT, 1000);
    }

    public void pause() {
        mHandler.removeMessages(WHAT);
    }

    public void stop() {
        mHandler.removeMessages(WHAT);
        mCurrentProgress = 0;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

}
