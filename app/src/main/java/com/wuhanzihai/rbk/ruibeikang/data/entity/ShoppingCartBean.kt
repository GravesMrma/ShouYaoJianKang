package com.wuhanzihai.rbk.ruibeikang.data.entity

data class ShoppingCartBean(
    val storeid: Int,
    val storename: String,
    val storetype: Int,
    val product_list:List<ProductItem>
)


data class ProductItem(
    val buyer_quota: Int,
    val cartid: Int,
    val invalid: Int,
    var number: Int,
    val original_price: String,
    val price: String,
    val product_id: Int,
    val product_image: String,
    val productname: String,
    val shopnumber: Int,
    val sku_id: Int,
    val skudata: List<SkudataItem>,
    val skuname: String,
    var isCheck:Boolean = false,
    var isManager:Boolean = false
)

data class SkudataItem(  val pid: String,  val vid: String)