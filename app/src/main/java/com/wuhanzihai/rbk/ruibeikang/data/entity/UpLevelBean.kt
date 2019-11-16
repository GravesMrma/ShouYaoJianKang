package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable


data class UpLevelBean(
    val toexamine: Toexamine,
    val topdisbutor: Topdisbutor
)

data class Toexamine(
    val address: String,
    val area: String,
    val avatoar: String,
    val city: String,
    val create_time: String,
    val id: Int,
    val name: String,
    val open_id: String,
    val phone: String,
    val pid: Int,
    val province: String,
    val status: Int,
    val totle_money: String,
    val type: Int,
    val uid: Int,
    val unid: String,
    val update_time: String,
    val wx_image: String
)

data class Topdisbutor(
    val address: String,
    val area: String,
    val avatoar: String,
    val city: String,
    val create_time: String,
    val id: Int,
    val name: String,
    val open_id: String,
    val phone: String,
    val pid: Int,
    val province: String,
    val status: Int,
    val totle_money: String,
    val type: Int,
    val uid: Int,
    val unid: String,
    val update_time: String,
    val wx_image: String
)