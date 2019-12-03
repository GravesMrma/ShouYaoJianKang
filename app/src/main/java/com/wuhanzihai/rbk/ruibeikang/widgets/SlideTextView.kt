package com.wuhanzihai.rbk.ruibeikang.widgets

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.TextView

class SlideTextView:TextView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var list = mutableListOf<String>()

    fun startSlide(list: MutableList<String>){
        this.list.clear()
        this.list.addAll(list)
        startSlideView()
    }

    private var handle = Handler()
    private var index = 0

    private var runnable = Runnable{
        startSlideView()
    }

    private fun startSlideView(){
        text = list[index%list.size]
        val animationSet = AnimationSet(true)
        val animation = TranslateAnimation(0f, 0f, 40f, 0f)
        animation.duration = 800
        animationSet.addAnimation(animation)
        animationSet.fillAfter = true
        startAnimation(animationSet)
        index++
        handle.postDelayed(runnable,3000)
    }
}