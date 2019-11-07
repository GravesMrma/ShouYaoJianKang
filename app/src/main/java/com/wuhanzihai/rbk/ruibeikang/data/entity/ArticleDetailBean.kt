package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 * Created by Zuriel Cotter on 2019/2/26.
 */

data class ArticleDetailBean(
    val add_time: String,
    val article_id: Int,
    val article_type: Int,
    val author: String,
    val cat_id: Int,
    val click: Int,
    val content: String,
    val description: String,
    val follow: Int,
    val fortypv: String,
    val hot_time: String,
    val is_hot: Int,
    val isshouchan: Int,
    val iszan: Int,
    val is_open: Int,
    val is_tuijian: Int,
    val keywords: List<String>,
    val publish_time: String,
    val sort: Int,
    val thumb: String,
    val title: String,
    val twospv: String
)
