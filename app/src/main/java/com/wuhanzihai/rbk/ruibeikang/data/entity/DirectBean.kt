package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class DirectBean(
    val item: List<DirectItem>,
    val page: Int,
    val totle: Int
)

data class DirectItem(
    val agent_id: Int,
    val agent_pid: Int,
    val birthday: String,
    val g_name:String,
    val card_id: Int,
    val card_no: String,
    val chunyu_pwd: String,
    val class_a_agent: Int,
    val class_b_agent: Int,
    val class_c_agent: Int,
    val create_time: String,
    val dg_id: Int,
    val direct: Int,
    val discount: String,
    val email: String,
    val head_pic: String,
    val height: Int,
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
    val userdirectcount: Int,
    val vip_card_id: Int,
    val weight: Int
)