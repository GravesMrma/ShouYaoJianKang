package com.wuhanzihai.rbk.ruibeikang.data.entity

data class ChatBean(
    val doctor: ChatDoctor,
    val question: List<MsgBean>,
    val status:Int
)

data class ChatDoctor(
        val clinic_name: String,
        val hospital: String,
        val image: String,
        val name: String,
        val title: String
)
