package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 * Created by Zuriel Cotter on 2019/2/26.
 */

data class HealthHabitsBean(
    val cat_id: Int,
    val cat_name: String,
    val cate_sort: Int,
    val href: String,
    val is_show: Int,
    val ischild: String,
    val laravel: Int,
    val parent_id: Int,
    val show_subclass: Int,
    val thumb: String,
    val child: List<HealthHabitsListItem>,
    var isCheck :Boolean
)

data class HealthHabitsListItem(
    val cat_id: Int,
    val cat_name: String,
    val cate_sort: Int,
    val href: String,
    val is_show: Int,
    val ischild: String,
    val laravel: Int,
    val parent_id: Int,
    val show_subclass: Int,
    val thumb: String
):Serializable