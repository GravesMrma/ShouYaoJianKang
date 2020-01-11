package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable


data class GoodsDetailBean(
        val after_subscribe_discount: String,
        val after_subscribe_price: String,
        val allow_discount: String,
        val attention_num: Int,
        val buy_url: String,
        val buyer_quota: Int,
        val category_fid: Int,
        val category_id: Int,
        val check_degree_discount: Int,
        val check_give_points: Int,
        val code: String,
        val collect: Int,
        val counterfeit_sales: Int,
        val date_added: String,
        val free_postage: Int,
        val give_points: Int,
        val has_custom: Int,
        val has_property: Int,
        val image: List<String>,
        val imagevadio: String,
        val info: String,
        val intro: String,
        val invoice: String,
        val is_after_subscribe_discount: Int,
        val is_hot: Int,
        val is_recommend: Int,
        val is_reservation: Int,
        val is_whitelist: Int,
        val last_edit_time: Int,
        val limit_end_time: String,
        val limit_time: String,
        val name: String,
        val open_return_point: Int,
        val original_price: String,
        val pcheck_time: Int,
        val platform_check: Int,
        val platform_status: Int,
        val point_exchange_num: Int,
        val point_price: String,
        val postage: String,
        val postage_template_id: Int,
        val postage_type: String,
        val price: String,
        val product_id: Int,
        val properties: String,
        val pv: Int,
        val quantity: Int,
        val recommend_title: String,
        val reservation_deposit: String,
        val sales: Int,
        val show_sku: String,
        val sold_time: Int,
        val soldout: String,
        val sort: Int,
        val special_product_type: Int,
        val status: String,
        val store_id: Int,
        val type: String,
        val uv: Int,
        val warranty: String,
        val sku: List<SkuItem>,
        val couponlist:List<GoodsCouponBean>,
        val propertydata: List<PropertydataItem>
)

data class SkuItem(
        val after_subscribe_discount: String,
        val code: String,
        val point_exchange_num: Int,
        val point_price: String,
        val price: String,
        val prime_cost_price: String,
        val product_id: Int,
        val properties: String,
        val quantity: Int,
        val return_point: String,
        val sales: Int,
        val sku_id: Int,
        val skuimg: String,
        val wholesale_sku_id: Int
)

data class PropertydataItem(
        val pid: Int,
        val name: String,
        val is_color: Int,
        val propertydatavalue: List<PropertydatavalueItem>
)

data class PropertydatavalueItem(
        val pts_id: Int,
        val pid: Int,
        val vid: Int,
        val pname: String,
        val valname: String,
        val is_color: Int
)


data class GoodsBuyBean(
//        val activity: List<Any>,
        val coupons: List<CouponBean>,
        val postage: Int,
        val product: ProductBean
//        val useradress: Useradress
) : Serializable

data class ProductBean(
        val after_subscribe_discount: String,
        val allow_discount: String,
        val buyer_quota: Int,
        val check_degree_discount: Int,
        val check_give_points: Int,
        val give_points: Int,
        val has_property: Int,
        val image: String,
        val is_after_subscribe_discount: Int,
        val is_reservation: Int,
        val last_edit_time: Int,
        val limit_end_time: Int,
        val limit_time: Int,
        val name: String,
        val intro:String,
        val original_price: String,
        val postage: String,
        val postage_template_id: Int,
        val postage_type: String,
        val price: String,
        val product_price: String,
        val product_status: String,
        val properties: String,
        val propertiesname: String,
        val quantity: Int,
        val reservation_deposit: String,
        val sku_id: Int,
        val skuprice: String,
        val number:String,
        val skuquantity: Int,
        val soldout: String,
        val special_product_type: Int,
        val store_id: Int,
        val tariff_price: String
) : Serializable

data class Useradress(
        val address: String,
        val address_id: Int,
        val city: String,
        val consignee: String,
        val district: String,
        val is_default: Int,
        val mobile: String,
        val province: String
) : Serializable