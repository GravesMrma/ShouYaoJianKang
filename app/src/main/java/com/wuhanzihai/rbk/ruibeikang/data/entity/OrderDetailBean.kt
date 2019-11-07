package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class OrderDetailBean(
        var countprice:Double,
        var orderlist: List<String>
):Serializable
