package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

//data class PhoneNumberBean(
//    val address: String,
//    val area: String,
//    val avatoar: String,
//    val city: String,
//    val create_time: String,
//    val extracttomoney: Double,
//    val id: Int,
//    val name: String,
//    val open_id: String,
//    val phone: String,
//    val pid: Int,
//    val province: String,
//    val status: Int,
//    val toexmaine_money: Double,
//    val totle_money: Double,
//    val type: Int,
//    val type_name: String,
//    val uid: Int,
//    val unid: String,
//    val update_time: String,
//    val wx_image: String
//)

data class PhoneNumberBean(
        val item: List<PhoneNumberItem>,
        val totle: Int
)

data class DistributionBean(
        val item: List<PhoneNumberItem>,
        val totle: Int
)

data class PhoneNumberItem(
    val apply_id: Int,
    val apply_remark: String,
    val apply_status: Int,
    val cb_id: Int,
    val count_number: Int,
    val create_time: String,
    val disbutor_id: Int,
    val distributor_id: Int,
    val distributor_pid: Int,
    val distributor_pidtype: Int,
    val distributor_type: Int,
    val end_cardno: String,
    val expressnumber: String,
    val is_shipped: Int,
    val jihuonumber: Int,
    val name: String,
    val phone: String,
    val number: Int,
    val shipped_date: Int,
    val start_cardno: String,
    val start_rmark: String,
    val topdisbutor_id: Int,
    val topname: String,
    val type_name: String,
    val vcbd_fid: String,
    val province: String,
    val city: String,
    val area: String,
    val vcbd_id: String
)
