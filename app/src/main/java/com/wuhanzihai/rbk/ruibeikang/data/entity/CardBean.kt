package com.wuhanzihai.rbk.ruibeikang.data.entity



data class CardRecord(
    val item: List<CardRecordItem>,
    val itemcount: Int
)

data class CardRecordItem(
    val agent_id: Int,
    val g_id: Int,
    val name: String,
    val g_name:String,
    val number: Int,
    val pone: String,
    val stock_id: Int,
    val toexamine: String,
    val create_time:String,
    val type: String,
    val type_status: String
)