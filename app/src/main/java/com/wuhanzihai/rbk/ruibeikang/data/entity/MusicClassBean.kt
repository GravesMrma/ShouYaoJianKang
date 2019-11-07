package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 * Created by Zuriel Cotter on 2019/2/26.
 */
data class MusicClassBean(
        var cat_id: Int,
        var cat_name: String,
        var parent_id: Int,
        var show_subclass: Int,
        var is_show: Int,
        var cate_sort: Int,
        var thumb: String,
        var type: Int,
        var href: String,
        var laravel: Int,
        var isCheck: Boolean = false)

data class MusicDetailBean(
    val item: List<MusicItem>,
    val totle: Int
)

data class MusicItem(
    val cate_id: Int,
    val is_hot: Int,
    val is_top: Int,
    val keywords: List<String>,
    val music_date: String,
    val music_desc: String,
    var music_duration: Int,
    val music_id: Int,
    val music_img: String,
    val music_number: Int,
    val music_startnumber: Int,
    val music_theme: String,
    val music_title: String,
    val music_tuijian: Int,
    val music_type: Int,
    var isPlaying: Boolean = false,
    val music_url: String
):Serializable