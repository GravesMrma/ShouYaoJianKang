package com.wuhanzihai.rbk.ruibeikang.data.entity


data class RebateRecordBean(
    val count: Int,
    val item: List<RebateRecordItem>
)

data class RebateRecordItem(
//    val agentorder_id: Int,
//    val currentagent: Int,
    val currentagent_money: String,
    val currentagent_prop: Int,
//    val is_check: Int,
    val order_id: Int,
    val order_no: String,
    val paid_time: Int,
    val product: List<RecordProduct>,
    val status: Int
//    val store_id: Int
)

data class RecordProduct(
    val discount: Int,
    val goodsname: String,
    val images: String,
//    val in_package_status: Int,
//    val is_comment: Int,
//    val is_fx: String,
//    val is_packaged: Int,
//    val is_present: Int,
//    val order_goods_id: Int,
//    val order_id: Int,
//    val point: Int,
//    val point_exchange_num: Int,
//    val presale_pro_price: String,
//    val pro_good_remark: RecordProGoodRemark,
    val pro_num: Int
//    val pro_price: String,
//    val pro_weight: Int,
//    val product_id: Int,
//    val return_point: String,
//    val return_status: Int,
//    val reward_id: Int,
//    val rights_status: Int,
//    val sku_data: String,
//    val sku_id: Int,
//    val subscribed_discount: String,
//    val tariff_price: String,
//    val type: Int
)

data class RecordProGoodRemark(
    val after_subscribe_discount: String,
    val allow_discount: String,
    val buyer_quota: Int,
    val check_degree_discount: Int,
    val check_give_points: Int,
    val give_points: Int,
    val has_property: Int,
    val image: List<String>,
    val is_after_subscribe_discount: Int,
    val is_fx: String,
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
    val skudata: List<Any>,
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
)

