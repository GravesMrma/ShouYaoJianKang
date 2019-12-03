package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable



data class UpLevelBean(
    val defaultagent: Defaultagent,
    val topagent: Topagent
)

data class Topagent(
    val agent_create_time: String,
    val create_time:String,
    val agent_id: Int,
    val card_no: String,
    val dg_id: Int,
    val direct_card: String,
    val g_id: String,
    val g_name: String,
    val head_pic: String,
    val nickname: String
)

data class Defaultagent(
    val agent_create_time: String,
    val agent_id: Int,
    val card_no: String,
    val dg_id: Int,
    val direct_card: String,
    val g_id: String,
    val g_name: String,
    val head_pic: String,
    val nickname: String
)