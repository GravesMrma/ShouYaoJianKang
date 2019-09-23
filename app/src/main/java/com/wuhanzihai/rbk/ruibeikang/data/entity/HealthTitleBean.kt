package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 * Created by Zuriel Cotter on 2019/2/26.
 */
data class HealthTitleBean(
        var cat_id: Int,
        var cat_name: String,
        var parent_id: Int,
        var show_subclass: Int,
        var is_show: Int,
        var cate_sort: Int,
        var thumb: String,
        var ischild: String,
        var isCheck: Boolean = false)