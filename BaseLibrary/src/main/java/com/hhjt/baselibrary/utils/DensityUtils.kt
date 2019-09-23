package com.hhjt.baselibrary.utils

import android.app.Activity
import android.content.Context
import android.util.TypedValue

import java.lang.reflect.Field

/**
 * 常用单位转换的辅助类
 */
object DensityUtils {

    /**
     * dp转px
     *
     * @param context
     * @param
     * @return
     */
    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.resources.displayMetrics).toInt()
    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    fun sp2px(context: Context, spVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.resources.displayMetrics).toInt()
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    fun px2dp(context: Context, pxVal: Float): Float {
        val scale = context.resources.displayMetrics.density
        return pxVal / scale
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxVal
     * @return
     */
    fun px2sp(context: Context, pxVal: Float): Float {
        return pxVal / context.resources.displayMetrics.scaledDensity
    }

    /**
     * 获得屏幕宽/高
     */
    fun getWindowHeight(acitvity: Activity): Int {
        return acitvity.windowManager.defaultDisplay.height
    }

    fun getWindowWidth(acitvity: Activity): Int {
        return acitvity.windowManager.defaultDisplay.width
    }

    fun getStatusBarHeight(context: Context?): Int {
        if (context == null)
            return 0
        var c: Class<*>? = null
        var obj: Any? = null
        var field: Field? = null
        var x = 0
        var statusBarHeight = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c!!.newInstance()
            field = c.getField("status_bar_height")
            x = Integer.parseInt(field!!.get(obj).toString())
            statusBarHeight = context.resources.getDimensionPixelSize(x)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

        return statusBarHeight
    }

}
