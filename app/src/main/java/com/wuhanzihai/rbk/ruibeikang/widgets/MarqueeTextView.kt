package com.wuhanzihai.rbk.ruibeikang.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class MarqueeTextView : TextView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )
    override fun isFocused(): Boolean {
        return true
    }
}