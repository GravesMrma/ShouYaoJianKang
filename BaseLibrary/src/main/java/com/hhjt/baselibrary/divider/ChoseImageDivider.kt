package com.hhjt.baselibrary.divider

import android.content.Context
import android.support.v4.content.ContextCompat
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration

/**
 * Created by wx on 2018/8/27
 */
class ChoseImageDivider constructor(var context: Context) : Y_DividerItemDecoration(context) {
    override fun getDivider(itemPosition: Int): Y_Divider {
        val divider: Y_Divider? = Y_DividerBuilder()
            .setRightSideLine(
                true,
                ContextCompat.getColor(context, android.R.color.transparent),
                10f,
                0f, 0f
            )
            .setBottomSideLine(
                true, ContextCompat.getColor(context, android.R.color.transparent),
                10f,
                0f, 0f
            )
            .create()
        return divider!!
    }
}