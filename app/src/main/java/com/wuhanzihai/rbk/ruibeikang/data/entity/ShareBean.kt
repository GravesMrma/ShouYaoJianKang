package com.wuhanzihai.rbk.ruibeikang.data.entity



data class ShareBean(
    val count: Int,
    val count_page: Int,
    val list: List<ShareItem>
)

data class ShareItem(
    val create_time: String,
    val price: String,
    val types_name: String,
    val types_title: String
)


