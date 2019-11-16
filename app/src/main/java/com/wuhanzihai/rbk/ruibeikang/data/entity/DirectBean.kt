package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class DirectBean(
    val item: List<DirectItem>,
    val page: Int,
    val totle: Int
)

data class DirectItem(
    val area: String,
    val card_id: Int,
    val card_no: String,
    val city: String,
    val create_time: String,
    val dg_name: String,
    val distributor_id: Int,
    val distributor_thereid: Int,
    val distributor_twoid: Int,
    val mobile: String,
    val name: String,
    val nickname: String,
    val open_id: String,
    val phone: String,
    val pone: String,
    val province: String,
    val relation_time: Int,
    val uid: Int
)