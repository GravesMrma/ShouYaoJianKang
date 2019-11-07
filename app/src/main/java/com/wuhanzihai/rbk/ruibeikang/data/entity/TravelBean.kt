package com.wuhanzihai.rbk.ruibeikang.data.entity

data class TravelBean(
    val item: List<TravelItem>,
    val totle: Int
)

data class TravelItem(
    val article_id: Int,
    val click: Int,
    val description: String,
    val follow: Int,
    val keywords: List<String>,
    val thumb: String,
    val title: String
)