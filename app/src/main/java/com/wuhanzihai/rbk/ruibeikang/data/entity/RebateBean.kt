package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class RebateBean(
    val address: String,
    val area: String,
    val avatoar: String,
    val city: String,
    val create_time: String,
    val extracttomoney: Double,
    val id: Int,
    val name: String,
    val open_id: String,
    val phone: String,
    val pid: Int,
    val province: String,
    val status: Int,
    val toexmaine_money: Double,
    val totle_money: Double,
    val type: Int,
    val type_name: String,
    val uid: Int,
    val unid: String,
    val update_time: String,
    val wx_image: String
)

data class IsRebateBean(val issettledin:Int,
                        val isinfo:Int)
