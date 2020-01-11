package com.wuhanzihai.rbk.ruibeikang.data.entity

data class SureOrderBean(
    val invalid: List<Any>,
    val storedata: List<Storedata>
)

data class Storedata(
    val free_shipping: Int,
    val product_list: List<Product>,
    val storeid: Int,
    val storename: String,
    val storetype: Int,
    val userstorecoupons: List<Any>,
    var coupons:List<CouponBean>
)

data class Product(
    val buyer_quota: Int,
    val cartid: Int,
    val invalid: Int,
    val invalidmsg: String,
    val number: Int,
    val price: String,
    val product_id: Int,
    val product_image: String,
    val productname: String,
    val sku_id: Int,
    val skudata: List<Skudata>,
    val skuname: String
)

data class Skudata(
    val pid: String,
    val vid: String
)