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
import android.util.SparseIntArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/2/17 11:48
 * 更新时间: 2022/2/17 11:48
 * 描述:
 */
public class MusicSwingView extends View {
    private static final int DEFAULT_SIZE = 30;
    private static final int DEFAULT_SWING_COUNT = 3;
    private static final int WHAT_MESSAGE = 100;
    private Paint mPaint;
    private int mSwingColor;
    private int mSwingCount = DEFAULT_SWING_COUNT;
    private int mItemWidth;
    private int mGapWidth;
    private int mRadius;
    private final SparseIntArray mArray = new SparseIntArray();
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == WHAT_MESSAGE) {
                for (int i = 0; i < mSwingCount; i++) {
                    int top = (int) (Math.random() * getWidth());
                    if (top == 0) {
                        top += dp2px(getContext(), 5);
                    }
                    mArray.put(i, top);
                }
                invalidate();
                mHandler.sendEmptyMessageDelayed(WHAT_MESSAGE, 150);
            }
            return false;
        }
    });

    public MusicSwingView(Context context) {
        this(context, null);
    }

    public MusicSwingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MusicSwingView);
        if (typedArray != null) {
            //   typedArray.getDimensionPixelOffset(R.styleable.MusicSwingView_msv_min_swing_height, );
            mSwingCount = typedArray.getInt(R.styleable.MusicSwingView_msv_swing_count, mSwingCount);
            if (mSwingCount <= 0) {
                mSwingCount = DEFAULT_SWING_COUNT;
            }
            mSwingColor = typedArray.getColor(R.styleable.MusicSwingView_msv_color, Color.WHITE);
            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mSwingColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasureSize(widthMeasureSpec, dp2px(getContext(), DEFAULT_SIZE));
        int height = getMeasureSize(heightMeasureSpec, dp2px(getContext(), DEFAULT_SIZE));
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int count = mSwingCount - 1;
        mGapWidth = dp2px(getContext(), 2f);
        mItemWidth = (w - mGapWidth * count) / mSwingCount;
        mRadius = mItemWidth / 2;
        Log.w("onSizeChanged--", mGapWidth + "--" + mItemWidth);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for (int i = 0; i < mSwingCount; i++) {
            canvas.drawRoundRect(mItemWidth * i + mGapWidth * i,
                    mArray.get(i), mItemWidth * i + mGapWidth * i + mItemWidth, getMeasuredHeight(), mRadius, mRadius, mPaint);
        }
    }

    private int getMeasureSize(int measureSpec, int defaultSize) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.AT_MOST) {
            result = Math.min(size, defaultSize);
        } else if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = defaultSize;
        }
        return result;
    }

    public void start() {
        mHandler.sendEmptyMessageDelayed(WHAT_MESSAGE, 100);
    }

    public void stop() {
        mHandler.removeMessages(WHAT_MESSAGE);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
