package com.hhjt.baselibrary.divider

import android.content.Context
import android.support.v4.content.ContextCompat
import com.yanyusong.y_divideritemdecoration.Dp2Px
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration

/**
 * Created by wx on 2018/8/27
 */
class GridDivider constructor(var context: Context) : Y_DividerItemDecoration(context) {
    override fun getDivider(itemPosition: Int): Y_Divider {
        var divider: Y_Divider? = null
        when (itemPosition % 2) {
            0 -> divider = Y_DividerBuilder()
                    .setLeftSideLine(true,
                            ContextCompat.getColor(context, android.R.color.transparent),
                            Dp2Px.convert(context, 8f).toFloat(), 0f, 0f)
                    .setTopSideLine(true,
                            ContextCompat.getColor(context, android.R.color.transparent),
                            Dp2Px.convert(context, 8f).toFloat(), 0f, 0f)
                    .setRightSideLine(true,
                            ContextCompat.getColor(context, android.R.color.transparent),
                            Dp2Px.convert(context, 4f).toFloat(), 0f, 0f)
                    .create()
            1 -> divider = Y_DividerBuilder()
                    .setLeftSideLine(true,
                            ContextCompat.getColor(context, android.R.color.transparent),
                            Dp2Px.convert(context, 4f).toFloat(), 0f, 0f)
                    .setTopSideLine(true,
                            ContextCompat.getColor(context, android.R.color.transparent),
                            Dp2Px.convert(context, 8f).toFloat(), 0f, 0f)
                    .setRightSideLine(true,
                            ContextCompat.getColor(context, android.R.color.transparent),
                            Dp2Px.convert(context, 8f).toFloat(), 0f, 0f)
                    .create()
        }
        return divider!!
    }
}