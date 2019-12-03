package com.wuhanzihai.rbk.ruibeikang.data.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

data class MsgBean(var type:Int,var name:String): MultiItemEntity {
    override fun getItemType(): Int {
        return type
    }
}