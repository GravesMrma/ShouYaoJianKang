package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 * Created by Zuriel Cotter on 2019/2/26.
 */

data class HealthFoodBean(
    val item: List<HealthTitleBean>,
    val title: Title
)

data class HealthFoodItem(
    val cat_id: Int,
    val cat_name: String,
    val cate_sort: Int,
    val is_show: Int,
    val ischild: String,
    val parent_id: Int,
    val show_subclass: Int,
    val thumb: String,
    val type: Int
)

data class Title(
    val cat_id: Int,
    val cat_name: String,
    val cate_sort: Int,
    val is_show: Int,
    val parent_id: Int,
    val show_subclass: Int,
    val thumb: String,
    val type: Int
)