package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

/**
 * Created by Zuriel Cotter on 2019/2/26.
 */
data class HealthClassBean(
        var totle: Int,
        var item: List<HealthClassItem>)

data class HealthClassItem(
        var mc_id: Int,
        var course_title: String,
        var course_img: String,
        var course_click: Int,
        var average_duration: Int,
        var course_date: Int,
        var keywords: List<String>)

data class HealthClassBannerBean(
        var totle: Int,
        var item: List<BannerEntity>)


data class HealthClassDetailBean(
        val average_duration: Int,
        val course_banner: String,
        val course_click: Int,
        val course_date: String,
        val course_desc: String,
        val course_img: String,
        val course_startnumber: Int,
        val course_title: String,
        val keywords: String,
        val mc_id: Int,
        val tuijian: Int
)

data class HealthClassDetailMusicBean(
        var totle: Int,
        var item: List<MusicItem>)

//data class HealthClassDetailMusicItem(
//        var totle: Int)