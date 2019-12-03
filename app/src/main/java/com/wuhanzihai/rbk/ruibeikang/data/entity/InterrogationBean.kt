package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 * Created by Zuriel Cotter on 2019/2/26.
 */
data class InterrogationBean(
    val desc: String,
    val order: List<InterrogationItem>
)

data class InterrogationItem(
    val create_time: Long,
    val docter_id: String,
    val doctor_content: String,
    val order_id: Int,
    val pay_time: Int,
    val price: String,
    val reading: Int,
    val status: Int
)