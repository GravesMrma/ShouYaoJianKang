package com.hhjt.baselibrary.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.hhjt.baselibrary.R
import java.lang.Exception
import kotlin.math.ceil

class LevelView : View {

    private var widt = 0
    private var heigh = 0

    private var maxLevel = 6 // 等级数量
    private var level = 0.0
    private var levelHeight = 30f // 读条高度
    private var maxScore = 0f
    private var score = 0f
    private var bgColor = 0 // 背景颜色
    private var isGradual = false  // 是否渐变
    private var startColor = 0 // 开始渐变颜色
    private var endColor = 0 // 结束渐变颜色
    private var startText = "V"

    private var lineColor = resources.getColor(R.color.black_33)
    private var mainColor = resources.getColor(R.color.orangeFFC7)

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0) {
        val ta = context!!.obtainStyledAttributes(attrs, R.styleable.LevelView, 0, 0)

        maxLevel = ta.getInteger(R.styleable.LevelView_maxLevel, 6)
        levelHeight = ta.getDimension(R.styleable.LevelView_levelHeight, 30f)
        maxScore = ta.getFloat(R.styleable.LevelView_maxScore, 0f)
        score = ta.getFloat(R.styleable.LevelView_score, 0f)
        bgColor = ta.getColor(R.styleable.LevelView_bgColor, Color.GRAY)
        isGradual = ta.getBoolean(R.styleable.LevelView_isGradual, false)
        startColor = ta.getColor(R.styleable.LevelView_startColor, Color.WHITE)
        endColor = ta.getColor(R.styleable.LevelView_endColor, Color.WHITE)
        try {
            startText = ta.getString(R.styleable.LevelView_startText)!!
        } catch (e: Exception) {
        }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

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

        // 计算等级
        var oneScore = maxScore / (maxLevel - 1) // 一级的分数是多少

        var l = (score / oneScore)
        level = ceil(l.toDouble())
        if ((l % 1) == 0f) {
            level++
        }

        Log.e("LevelView", level.toString())

        drawBg(canvas!!)
    }

    private fun drawBg(canvas: Canvas) {
        // bg的画笔
        var paint = Paint()
        paint.color = bgColor

        // bg的区域
        var bgRectf = RectF()
        bgRectf.top = 0f
        bgRectf.bottom = levelHeight
        bgRectf.left = 0f
        bgRectf.right = widt.toFloat()

        canvas.drawRoundRect(bgRectf, levelHeight, levelHeight, paint)

        drawScore(canvas)
    }

    private fun drawScore(canvas: Canvas) {

        // Score的画笔
        var paint = Paint()
        paint.color = Color.GREEN

        // Score的区域
        var bgRectf = RectF()
        bgRectf.top = 0f
        bgRectf.bottom = levelHeight
        bgRectf.left = 0f

        if (score == 100f) {
            bgRectf.right = widt.toFloat() * (score / 100f)
        } else {
            bgRectf.right = ((widt.toFloat() * 0.9f) * (score / 100f)) + (widt.toFloat() * 0.05f)
        }


        val colors = IntArray(2)
        val positions = FloatArray(2)

        // 第1个点
        colors[0] = startColor
        positions[0] = 0f

        // 第2个点
        colors[1] = endColor
        positions[1] = 1f

        val shader = LinearGradient(
                0f, 0f,
                widt.toFloat(), 0f,
                colors,
                positions,
                Shader.TileMode.MIRROR)
        paint.shader = shader
        canvas.drawRoundRect(bgRectf, levelHeight, levelHeight, paint)

        drawText(canvas)

    }

    private fun drawText(canvas: Canvas) {
        var oneWith = (widt.toFloat() * 0.9f) / (maxLevel - 1)
        var paddingH = (widt.toFloat() * 0.05f)
        var paddingV = (levelHeight * 0.2f)

        var linePaint = Paint()
        linePaint.color = lineColor
        linePaint.style = Paint.Style.FILL
        linePaint.isAntiAlias = true

        val textPaint = Paint()
        textPaint.textSize = 28f
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.isAntiAlias = true

        var textBgPaint = Paint()

        for (i in 0..maxLevel) {
            // 在等级之前
            if (i < (level - 1)) {
                textPaint.color = mainColor
                textBgPaint.color = Color.TRANSPARENT
            } else if (i == (level.toInt() - 1)) {
                textPaint.color = Color.WHITE
                textBgPaint.color = mainColor
            } else {
                textPaint.color = bgColor
                textBgPaint.color = Color.TRANSPARENT
            }

            var w = (oneWith * i) + paddingH
            canvas.drawRect(w, paddingV + 0, w + 3, levelHeight - paddingV, linePaint)

            val rectF = RectF(w - 35f, levelHeight + 20, w + 35f, levelHeight + 60)

            //计算baseline
            val fontMetrics = textPaint.fontMetrics
            val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
            val baseline = rectF.centerY() + distance

            canvas.drawRoundRect(rectF, 10f, 10f, textBgPaint)
            canvas.drawText(startText + (i + 1), rectF.centerX(), baseline, textPaint)
        }
    }

    fun setScore(score:Float){
        this.score = score
        invalidate()
    }
}