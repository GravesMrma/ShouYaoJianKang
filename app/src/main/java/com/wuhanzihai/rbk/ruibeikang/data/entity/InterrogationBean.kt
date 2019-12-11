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
    val create_time: String,
    val docter_id: String,
    val doctor_content: DoctorContent,
    val order_id: Int,
    val pay_time: String,
    val price: String,
    val content:String,
    val reading: Int,
    val status: Int
)

data class DoctorContent(
    val achievement: String,
    val clinic_name: String,
    val description: String,
    val education: String,
    val error: Int,
    val error_msg: String,
    val fans_num: Int,
    val good_at: String,
    val good_rate: Double,
    val hospital: String,
    val hospital_grade: String,
    val id: String,
    val image: String,
    val is_famous_doctor: Boolean,
    val name: String,
    val price: Int,
    val recommend_rate: Int,
    val reply_num: Int,
    val reward_num: Int,
    val solution_score: Int,
    val tags: List<String>,
    val tel_online: Boolean,
    val tel_price: String,
    val msg: String,
    val title: String,
    val welcome: String
)

data class DoctorDetail(
    val achievement: String,
    val clinic_name: String,
    val description: String,
    val education: String,
    val error: Int,
    val error_msg: String,
    val fans_num: Int,
    val good_at: String,
    val good_rate: Double,
    val hospital: String,
    val hospital_grade: String,
    val id: String,
    val image: String,
    val is_famous_doctor: Boolean,
    val name: String,
    val price: Int,
    val recommend_rate: Int,
    val reply_num: Int,
    val reward_num: Int,
    val solution_score: Int,
    val tags: List<String>,
    val tel_online: Boolean,
    val tel_price: String,
    val title: String,
    val welcome: String
)