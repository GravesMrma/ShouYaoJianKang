package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class CouponBean(
    val card_no: String,
    val cname: String,
    val coupon_id: Int,
    val delete_flg: Int,
    val description: String,
    val end_time: Int,
    val face_money: String,
    val give_order_id: Int,
    val id: Int,
    val is_all_product: Int,
    val is_expire_notice: Int,
    val is_share: Int,
    val is_use: Int,
    val is_valid: Int,
    val limit_money: String,
    val saleroom_id: Int,
    val start_time: Int,
    val store_id: Int,
    val timestamp: Int,
    val todate: Int,
    val type: Int,
    val uid: Int,
    val use_order_id: Int,
    val use_time: Int,
    var isShow: Boolean = false
): Serializable

data class GoodsCouponBean(
    val coupon_date: Int,
    val coupon_date_type: Int,
    val coupon_enttime: Int,
    val coupon_id: Int,
    val coupon_starttime: Int,
    val description: String,
    val end_time: Int,
    val face_money: String,
    val is_all_product: Int,
    val is_expire_notice: Int,
    val limit_money: String,
    val most_have: Int,
    val name: String,
    val order_one: Int,
    val order_price: String,
    val salesroom_id: Int,
    val start_time: Int,
    val status: Int,
    val store_id: Int,
    val timestamp: String,
    val total_amount: Int,
    val type: Int,
    val uid: Int,
    val used_number: Int,
    val user_all: Int,
    val usercouponcount: Int,
    var isShow: Boolean = false
)


