package com.huxiaobai.progressapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.util.SparseIntArray
import android.view.View

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/2/21 13:50
 * 更新时间: 2022/2/21 13:50
 * 描述:
 */
open class MusicSwingCompat :
    View {
    companion object {
        const val DEFAULT_SIZE: Int = 30
        const val DEFAULT_SWING_COUNT = 3
        const val DEFAULT_GAP_WIDTH = 1.5F
        const val WHAT_MESSAGE = 100
        private fun dp2Px(context: Context, dpValues: Float): Int {
            val metrics = context.resources.displayMetrics
            return (metrics.density * dpValues + 0.5f).toInt()
        }
    }
    constructor(context: Context) : this(context, null)
    private var mSwingColor: Int
    private var mSwingCount: Int = 0
    private var mSwingGapWidth: Int = 0
    private var mItemWidth: Int = 0
    private var mSingGapWidth: Int
    private var mRadius: Int = 0
    private val mArray: SparseIntArray = SparseIntArray()
    private val mPaint: Paint = Paint()
    private val mHandler: Handler = Handler(Looper.getMainLooper(), Handler.Callback {
        if (it.what == WHAT_MESSAGE) {
            for (i in 0 until mSwingCount) {
                var top: Int = (Math.random() * width).toInt()
                if (top == 0) {
                    top += dp2Px(context, 5f)
                }
                mArray.put(i, top)
            }
            invalidate()
            start()
        }
        return@Callback false
    })


    @SuppressLint("CustomViewStyleable")
    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MusicSwingCompat)
        mSwingCount =
            typedArray.getInt(R.styleable.MusicSwingCompat_msc_swing_count, DEFAULT_SWING_COUNT)
        mSwingColor = typedArray.getColor(R.styleable.MusicSwingCompat_msc_color, Color.WHITE)
        mSingGapWidth = typedArray.getDimensionPixelOffset(
            R.styleable.MusicSwingCompat_msc_gap_width,
            dp2Px(context, DEFAULT_GAP_WIDTH)
        )
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initPaint()
    }

    private fun initPaint() {
        mPaint.color = mSwingColor
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.style = Paint.Style.FILL
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = getSize(widthMeasureSpec)
        val height = getSize(heightMeasureSpec)
        val size = width.coerceAtMost(height)
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val gapCount = mSwingCount - 1
        mSwingGapWidth = gapCount * mSingGapWidth
        mItemWidth = (w - mSwingGapWidth) / mSwingCount
        mRadius = mItemWidth / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (i in 0 until mSwingCount) {
            canvas?.drawRoundRect(
                (mItemWidth * i + mSingGapWidth * i).toFloat(),
                mArray.get(i).toFloat(),
                (mItemWidth * i + mSingGapWidth * i + mItemWidth).toFloat(),
                measuredHeight.toFloat(),
                mRadius.toFloat(),
                mRadius.toFloat(),
                mPaint
            )
        }
    }


    private fun getSize(
        measureSpec: Int,
        defaultSize: Int = DEFAULT_SIZE
    ): Int {
        val result: Int
        val size = MeasureSpec.getSize(measureSpec)
        result = when (MeasureSpec.getMode(measureSpec)) {
            MeasureSpec.AT_MOST -> {
                size.coerceAtMost(defaultSize)
            }
            MeasureSpec.EXACTLY -> {
                size
            }
            else -> {
                DEFAULT_SIZE
            }
        }
        Log.w("getSize--", "{$result}")
        return result

    }

    fun start() {
        mHandler.sendEmptyMessageDelayed(WHAT_MESSAGE, 150)
    }

    private fun stop() {
        mHandler.removeMessages(WHAT_MESSAGE)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }


}