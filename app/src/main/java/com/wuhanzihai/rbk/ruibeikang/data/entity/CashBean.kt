package com.wuhanzihai.rbk.ruibeikang.data.entity

data class CashDetailBean(
    val agent: AgentBean
)

data class AgentBean(
    val agent_id: Int,
    val aplliy_money: String,
    val money: String,
    val user_id: Int
)

data class ApplyCashRecordBean(
    val all_money: String,
    val list: List<ApplyCashRecordItem>
)

data class ApplyCashRecordItem(
        val preentry_level: Int,
        val price: String,
        val create_time:String,
        val types: Int,
        val types_name: String
)


data class ApplyCashListDetail(
    val desc: String,
    val list: List<ApplyCashListDetailItem>,
    val money: String
)

data class ApplyCashListDetailItem(
    val create_time: String,
    val now_status: String,
    val short_desc: String
)

data class ApplyCashListBean(
    val desc: String,
    val list: List<ApplyCashListItem>
)

data class ApplyCashListItem(
    val create_time: String,
    val desc: String,
    val error_desc: String,
    val id: Int,
    val money: String,
    val status: String
)