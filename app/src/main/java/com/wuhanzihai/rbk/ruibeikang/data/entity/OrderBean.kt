package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class OrderBean(
        val item: List<OrderItem>,
        val totle: Int
) : Serializable

data class OrderItem(
        val add_time: Int,
        val address: String,
        val address_tel: String,
        val address_user: String,
        val bak: String,
        val cancel_method: Int,
        val cancel_time: Int,
        val comment: String,
        val data_money: Int,
        val delivery_time: Int,
        val float_amount: String,
        val goodslist: List<Goodslist>,
        val is_point_exchange: Int,
        val is_reserved: Int,
        val member_card: String,
        val order_id: Int,
        val order_no: String,
        val order_paid_expire: Int,
        val paid_time: Int,
        val pay_money: String,
        val payment_method: String,
        val point_exchange_num: Int,
        val postage: String,
        val presale_order_id: Int,
        val pro_count: Int,
        val pro_num: Int,
        val reach_type: Int,
        val receive_time: Int,
        val refund_time: Int,
        val reservation_deposit: String,
        val sent_time: Int,
        val shipping_method: String,
        val star: Int,
        val status: Int,
        val store_comment: Int,
        val store_id: Int,
        val sub_total: String,
        val total: String,
        val type: Int,
        val uid: Int
) : Serializable

data class Goodslist(
        val discount: Int,
        val in_package_status: Int,
        val is_comment: Int,
        val is_packaged: Int,
        val is_present: Int,
        val order_goods_id: Int,
        val order_id: Int,
        val point: Int,
        val point_exchange_num: Int,
        val presale_pro_price: String,
        val pro_good_remark: ProGoodRemark,
        val pro_num: Int,
        val pro_price: String,
        val pro_weight: Int,
        val product_id: Int,
        val return_point: String,
        val return_status: Int,
        val reward_id: Int,
        val rights_status: Int,
//    val sku_data: String,
        val sku_id: Int,
        val subscribed_discount: String,
        val tariff_price: String,
        val type: Int
) : Serializable

data class ProGoodRemark(
        val after_subscribe_discount: String,
        val allow_discount: String,
        val buyer_quota: Int,
        val check_degree_discount: Int,
        val check_give_points: Int,
        val give_points: Int,
        val has_property: Int,
        val image: String,
        val is_after_subscribe_discount: Int,
        val is_quota: Int,
        val is_reservation: Int,
        val last_edit_time: Int,
        val limit_end_time: Int,
        val limit_time: Int,
        val name: String,
        val number: Int,
        val original_price: String,
        val postage: String,
        val postage_template_id: Int,
        val postage_type: String,
        val price: String,
        val product_id: Int,
        val product_price: String,
        val product_status: String,
        val productsku_id: Any,
        val properties: Any,
        val quantity: Int,
        val reservation_deposit: String,
        val sku_id: Int,
        val skuname: String,
        val skuprice: Any,
        val skuquantity: Any,
        val soldout: String,
        val special_product_type: Int,
        val store_id: Int,
        val store_name: String,
        val store_status: String,
        val storetype: Int,
        val tariff_price: String,
        val user_id: Int,
        val usercart_id: Int
) : Serializable

data class Logistics(
        val code: String,
        val `data`: List<LogisticsData>,
        val no: String,
        val searchtime: String,
        val status: Int
)

data class LogisticsData(
        val context: String,
        val time: String
)

data class PriceBean(val price: String)