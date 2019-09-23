package com.wuhanzihai.rbk.ruibeikang.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.wuhanzihai.rbk.ruibeikang.R

class TrapezoidView : View {
    private var widt = 0
    private var heigh = 0
    private var paint = Paint()
    private var path = Path()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

        paint.color = resources.getColor(R.color.green_08)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        widt = w
        heigh = h
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

        path.moveTo(0f,0f)
        path.lineTo(heigh/2f,heigh.toFloat())
        path.lineTo(widt-(heigh/2f),heigh.toFloat())
        path.lineTo(widt.toFloat(),0f)

        path.close()

        canvas!!.drawPath(path,paint)
    }


    private fun sin(num: Int): Float {
        return Math.cos(num * Math.PI / 180).toFloat() * -1
    }

    private fun cos(num: Int): Float {
        return Math.sin(num * Math.PI / 180).toFloat()
    }

}