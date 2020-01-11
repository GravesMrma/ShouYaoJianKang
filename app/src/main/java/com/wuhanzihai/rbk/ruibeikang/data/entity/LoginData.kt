package com.wuhanzihai.rbk.ruibeikang.data.entity

data class LoginData(
        var birthday: String,
        var discount: String,
        var head_pic: String = "http://www.hcjiankang.com/androidimg/mid_icon_shuaige_s.png",
        var height: Int,
        var is_activation: Int,
        var is_lock: Int,
        var level: Int,
        var mobile: String,
        var nickname: String,
        var pay_points: String,
        var rel_code: String,
        var rel_name: String,
        var sex: Int,
        var sys_point: Any,
        var total_amount: String,
        var user_id: Int,
        var vip_card_id: Int,
        var weight: Int,
        var first_login: Int,
        var token: String,
        val usercouponcount:Int)