package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class OrderPayBean(
    val notify_url: String,
    val order_id: String,
    val order_order_amount: String,
    val order_sn: String,
    val order_string: String,
    val url: String
)