package com.hhjt.baselibrary.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.hhjt.baselibrary.R
import java.util.ArrayList

class WaterView : View {
    private var heigh = 0
    private var widt = 0

    private var centerRadius = 0f // 中心园的半径
    private var maxDistance = 0f // 最大的宽度
    private var distance = 2 //每次圆递增间距

    private var centerColor = 0 //  中心颜色
    private var spreadColor = 0 //  扩散颜色
    private var ringNum = 3 //每次圆递增间距
    private var scale = 0.8f // 中心园的比例


    private var paintCenter: Paint = Paint()
    private var paint: Paint = Paint()

    private val listRing = ArrayList<ItemRing>()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val ta = context!!.obtainStyledAttributes(attrs, R.styleable.WaterView, 0, 0)
        centerColor = ta.getColor(R.styleable.WaterView_centerColor,Color.WHITE)
        spreadColor = ta.getColor(R.styleable.WaterView_spreadColor,Color.WHITE)
        ringNum = ta.getInteger(R.styleable.WaterView_ringNum,3)
        scale = ta.getFloat(R.styleable.WaterView_ringRatio,0.7f)
        distance = ta.getInteger(R.styleable.WaterView_ringSpeed,2)
        paintCenter.color = centerColor
        paint.color = spreadColor
        paintCenter.isAntiAlias = true
        paint.isAntiAlias = true
        listRing.add(ItemRing(255, 0f))
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        widt = w
        heigh = h

        centerRadius = widt / 2 * scale

        maxDistance = widt / 2 * (1 - scale)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 循环外圈圆
        for (itemRing in listRing) {
            // 绘制外圈圆
            paint.alpha = itemRing.alpha
            canvas!!.drawCircle(widt.toFloat() / 2, heigh.toFloat() / 2, itemRing.width + centerRadius, paint)
            if (itemRing.width < widt / 2) {
                itemRing.width = itemRing.width + distance

                itemRing.alpha = 255- (255 * (itemRing.width / maxDistance)).toInt()
            }
        }

        if (listRing.last().width >= maxDistance / ringNum) {
            listRing.add(ItemRing(255, 0f))
        }

        if (listRing.first().width >= maxDistance) {
            listRing.removeAt(0)
        }

        canvas!!.drawCircle(widt.toFloat() / 2, heigh.toFloat() / 2, centerRadius, paintCenter)
        postInvalidateDelayed(20)
    }

    data class ItemRing(var alpha: Int
                        , var width: Float)

}