package com.wuhanzihai.rbk.ruibeikang.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

class LineView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var mH = 0
    private var mW = 0
    private var paint = Paint()
    private var paint1 = Paint()

    private var textPaint = Paint()
    private var textPaint1 = Paint()


    init {
        paint.color = Color.RED
        paint.strokeWidth = 3f
        paint.isAntiAlias = true

        paint1.color = Color.BLUE
        paint1.strokeWidth = 3f
        paint1.isAntiAlias = true

        textPaint.color = Color.BLACK
        textPaint.textSize = 28f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mW = w
        mH = h - padding - padding
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    private fun measureHeight(measureSpec: Int): Int {
        var result = 0
        var mode = MeasureSpec.getMode(measureSpec);
        var size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size
        } else {
            result = 75
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size)
            }
        }
        return result
    }

    private fun measureWidth(measureSpec: Int): Int {
        var result = 0;
        var mode = MeasureSpec.getMode(measureSpec)
        var size = MeasureSpec.getSize(measureSpec)

        if (mode == MeasureSpec.EXACTLY) {
            result = size
        } else {
            result = 75;//根据自己的需要更改
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawLine(0f, mH.toFloat() * last + padding, mW.toFloat() / 2, mH.toFloat() * count + padding, paint)
        canvas.drawLine(mW.toFloat() / 2, mH.toFloat() * count + padding, mW.toFloat(), mH.toFloat() * next + padding, paint)

        val rectF = RectF(0f, mH.toFloat() * last, mW.toFloat(), mH.toFloat() * count)

        //计算baseline
        val fontMetrics = textPaint.fontMetrics
        val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseline = mH.toFloat() * count + padding - distance - distance

        canvas.drawText("${max}°", mW.toFloat() / 2, baseline, textPaint)

        canvas.drawLine(0f, mH.toFloat() * last1 + padding, mW.toFloat() / 2, mH.toFloat() * count1 + padding, paint1)
        canvas.drawLine(mW.toFloat() / 2, mH.toFloat() * count1 + padding, mW.toFloat(), mH.toFloat() * next1 + padding, paint1)

        //计算baseline
        val baselineb = mH.toFloat() * count1 + padding + distance + distance + distance
        canvas.drawText("${min}°", mW.toFloat() / 2, baselineb, textPaint)
    }

    private var max = ""
    private var min = ""
    private var padding = 50

    private var last = 0f
    private var count = 0f
    private var next = 0f

    private var last1 = 0f
    private var count1 = 0f
    private var next1 = 0f

    fun setData(max: String, last: Float, count: Float, next: Float, min: String, last1: Float, count1: Float, next1: Float) {
        this.max = max
        this.min = min
        this.last = 1 - last
        this.count = 1 - count
        this.next = 1 - next
        this.last1 = 1 - last1
        this.count1 = 1 - count1
        this.next1 = 1 - next1
        invalidate()
    }
}