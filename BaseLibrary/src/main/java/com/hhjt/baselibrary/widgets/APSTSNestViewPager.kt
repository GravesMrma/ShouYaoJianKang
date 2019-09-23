package com.hhjt.baselibrary.widgets

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by linhonghong on 2015/8/10.
 */
class APSTSNestViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    private var mNoFocus = true //if true, keep View don't move

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (mNoFocus) {
            false
        } else super.onInterceptTouchEvent(event)
    }

    fun setNoFocus(b: Boolean) {
        mNoFocus = b
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var height = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility !== View.GONE) {
                child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                val h = child.measuredHeight
                if (h > height) {
                    height = h
                }
            }
        }

        var heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (mNoFocus) {
            false
        } else super.onTouchEvent(arg0)
    }
}