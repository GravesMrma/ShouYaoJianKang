package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class OrderBean(
        var res:Int,
        var list: List<GoodBean>
) : Serializable

data class GoodBean(
        var res:Int,
        var name:String,
        var price:String
):Serializable