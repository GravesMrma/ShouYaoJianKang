package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable


data class StoreDateBean(
    val day: String,
    val list: MutableList<StoreTimeBean>
)

data class StoreTimeBean(val time:String)


