package com.wuhanzihai.rbk.ruibeikang.data.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

data class MsgBean(
                   var name:String,
                   val `file`: String,
                   val status: Int,
                   val time: String,
                   val type: String,
                   val text: String,
                   var types: Int
): MultiItemEntity {
    override fun getItemType(): Int {
        return types
    }
}