package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable


data class RebateBean(
        val agent_create_time: String,
        val agent_id: Int,
        val agent_pid: Int,
        val birthday: String,
        val chunyu_pwd: String,
        val create_time: String,
        val default_direct_product: Int,
        val default_indirect_product: Int,
        val applycardnumber:Int,
        val dg_id: Int,
        val discount: String,
        val email: String,
        val g_id: Int,
        val g_name: String,
        val g_pid: Int,
        val head_pic: String,
        val height: Int,
        val identical_price: Int,
        val is_activation: Int,
        val is_lock: Int,
        val last_ip: String,
        val last_login: Int,
        val level: Int,
        val mobile: String,
        val money: String,
        val nickname: String,
        val oauth: String,
        val openid: String,
        val password: String,
        val pay_points: String,
        val reg_chunyu: String,
        val reg_time: Int,
        val rel_code: String,
        val rel_name: String,
        val sex: Int,
        val status: String,
        val stock: Int,
        val sys_point: String,
        val token: String,
        val total_amount: String,
        val totlemoney: String,
        val user_id: Int,
        val vip_card_id: Int,
        val weight: Int
)

data class IsRebateBean(val is_agent: Int)

data class RebateLevelBean(
        val applycardnumber: Int,
        val default_direct_product: Int,
        val default_indirect_product: Int,
        val g_id: Int,
        val g_name: String,
        val g_pid: Int,
        val identical_price: Int,
        val ischild: String
)

data class RebateLevelRecordBean(
    val item: List<LevelRecordItem>,
    val itempage: Int
)

data class LevelRecordItem(
    val agent_id: Int,
    val apply_grade_id: Int,
    val apply_name: String,
    val apply_pone: String,
    val applycardnumber: Int,
    val create_time: String,
    val default_direct_product: Int,
    val default_indirect_product: Int,
    val g_id: Int,
    val g_name: String,
    val g_pid: Int,
    val identical_price: Int,
    val status: String,
    val toexamineremark: String
)
