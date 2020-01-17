package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class OrderPayBean(
    val order_id: String,
    val order_order_amount: String,
    val order_sn: String,
    val order_string: String,
    val url: String,
    val appid: String,
    val noncestr: String,
    val `package`: String,
    val partnerid: String,
    val prepayid: String,
    val sign: String,
    val timestamp: String,
    val hashsign:String

)
