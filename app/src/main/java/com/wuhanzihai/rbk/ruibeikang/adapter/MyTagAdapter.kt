package com.wuhanzihai.rbk.ruibeikang.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.wuhanzihai.rbk.ruibeikang.R
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

class MyTagAdapter : TagAdapter<String> {
    private var context: Context? = null

    constructor(list: MutableList<String>, context: Context) : super(list) {
        this.context = context
    }

    override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
        var tv = LayoutInflater.from(context).inflate(
                R.layout.item_archives_tag,
                parent,
                false
        ) as TextView
        tv.text = t
        return tv
    }

    fun setSelectIndex(index:Int){

    }
}