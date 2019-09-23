package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 * Created by Zuriel Cotter on 2019/2/26.
 */
data class HealthListBean(
        var totle: Int,
        var item: List<HealthListItem>)

data class HealthListItem(
        var article_id: Int,
        var title: String,
        var description: String,
        var keywords: List<String>,
        var thumb: String,
        var click: Int,
        var follow: Int)