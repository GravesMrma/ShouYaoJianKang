package com.wuhanzihai.rbk.ruibeikang.data.entity

data class CollectBean(val mc_id:Int,
                       val course_title:String,
                       val course_banner:String,
                       val course_img:String,
                       val course_click:Int,
                       val average_duration:Int,
                       val course_date:Long,
                       val keywords:MutableList<String>)