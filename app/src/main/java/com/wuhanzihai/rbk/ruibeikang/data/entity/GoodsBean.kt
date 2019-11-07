package com.wuhanzihai.rbk.ruibeikang.data.entity

data class GoodsBean(
        val buyer_quota: Int,
        val category_fid: Int,
        val category_id: Int,
        val code: String,
        val has_property: Int,
        val image: String,
        val intro: String,
        val limit_end_time: String,
        val limit_time: String,
        val name: String,
        val original_price: String,
        val point_exchange_num: Int,
        val point_price: String,
        val price: String,
        val product_id: Int,
        val quantity: Int,
        val reservation_deposit: String,
        val special_product_type: Int,
        val is_reservation: Int,
        val store_id: Int,
        val counterfeit_sales: Int,
        val sales: Int,
        val type: String
)

data class GoodsResult(val item:List<GoodsBean>)