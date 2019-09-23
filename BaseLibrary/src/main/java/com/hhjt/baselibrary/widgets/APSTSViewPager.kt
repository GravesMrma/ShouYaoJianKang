package com.hhjt.baselibrary.widgets

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by linhonghong on 2015/8/10.
 */
class APSTSViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    private var mNoFocus = true //if true, keep View don't move

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (mNoFocus) {
            false
        } else super.onInterceptTouchEvent(event)
    }

    fun setNoFocus(b: Boolean) {
        mNoFocus = b
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (mNoFocus) {
            false
        } else super.onTouchEvent(arg0)
    }
}