package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class MineServiceBean(
        var title:String,
        var res:Int
) : Serializable

data class MineAdv(var item:MutableList<String>)