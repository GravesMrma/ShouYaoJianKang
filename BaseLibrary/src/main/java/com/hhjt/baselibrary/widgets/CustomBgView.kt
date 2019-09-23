package com.hhjt.baselibrary.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.hhjt.baselibrary.R

class CustomBgView : View {

    private var widt = 0
    private var heigh = 0

    private var topCorners = 0f
    private var fuckColor = 0 // 背景颜色
    private var isRound = false  //

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0) {

        val ta = context!!.obtainStyledAttributes(attrs, R.styleable.CustomBgView, 0, 0)

        topCorners = ta.getDimension(R.styleable.CustomBgView_topCorners, 20f)
        fuckColor = ta.getColor(R.styleable.CustomBgView_fuckColor, Color.WHITE)
        isRound = ta.getBoolean(R.styleable.CustomBgView_isRound, false)

    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        widt = w
        heigh = h
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        Log.e("onSizeChanged", "onSizeChanged$heightMeasureSpec")

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

        drawTop(canvas!!)
    }



    private fun drawTop(canvas: Canvas) {
        var paint = Paint()
        paint.color = fuckColor
        paint.isAntiAlias = true
        canvas.drawCircle(topCorners, topCorners, topCorners, paint)
        canvas.drawCircle(widt - topCorners, topCorners, topCorners, paint)

        var path = Path()
        path.moveTo(0f, topCorners)

        path.lineTo(0f, heigh - topCorners)

        path.quadTo(widt.toFloat() / 2, heigh + topCorners, widt.toFloat() + 0, heigh - topCorners)

        path.lineTo(widt.toFloat(), heigh - topCorners)

        path.lineTo(widt.toFloat(), topCorners)

        path.lineTo(widt - topCorners, 0f)

        path.lineTo(topCorners, 0f)

        canvas.drawPath(path, paint)

    }

}