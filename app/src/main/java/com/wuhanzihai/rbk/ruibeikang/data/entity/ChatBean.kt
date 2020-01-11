package com.wuhanzihai.rbk.ruibeikang.data.entity

data class ChatBean(
    val doctor: ChatDoctor,
    val question: List<MsgBean>,
    val status:Int,
    val remain_num:Int,
    val person_id:Int,
    val remain_time:Double
)

data class ChatDoctor(
        val clinic_name: String,
        val hospital: String,
        val image: String,
        val name: String,
        val title: String
//        val tags:MutableList<String>
)
