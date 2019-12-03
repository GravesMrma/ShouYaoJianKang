package com.wuhanzihai.rbk.ruibeikang.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

class MyViewPageAdapter(var list: MutableList<View>) : PagerAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(list[position])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(list[position])
        return list[position]
    }
}