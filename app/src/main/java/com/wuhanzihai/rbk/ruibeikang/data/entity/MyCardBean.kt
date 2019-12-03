package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class MyCardBean(
    val bankcard: Bankcard,
    val uaer_agent_grade: UaerAgentGrade,
    val vipcard: Vipcard
)

data class Bankcard(
    val bank_card_name: String,
    val bank_name: String,
    val card_id: Int,
    val card_number: String,
    val card_type: String,
    val card_type_name: String,
    val user_id: Int
)

data class UaerAgentGrade(
    val applycardnumber: Int,
    val default_direct_product: Int,
    val default_indirect_product: Int,
    val dg_id: Int,
    val direct_card: Int,
    val g_id: Int,
    val g_name: String,
    val g_pid: Int,
    val identical_price: Int
)

data class Vipcard(
    val activation_time: Long,
    val card_id: String,
    val is_actica: Int,
    val password: String,
    val vip_card_id: Int
)