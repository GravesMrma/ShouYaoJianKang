package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class MallBean(var banner:BannerItem,
                    var tjcategory:MallGoods,
                    var hotcategory:HotcategoryBean,
                    var coupon:CouponDetail,
                    var newpeople:NewPeopleDetail,
                    var theme:List<ThemeItemDetail>,
                    var proadcast:ProadCastDetail): Serializable

data class BannerItem(
        var totle: Int,
        var item: List<BannerEntity>)

data class MallGoods(
        var totle: Int,
        var item: List<MallGoodsItem>
) : Serializable

data class MallGoodsItem(var id: Int,
                         var pid: Int,
                         var name: String,
                         var pname: String,
                         var icon: String)

data class CouponDetail(
        var totle: Int,
        var item: List<BannerEntity>
)

data class NewPeopleDetail(
        var totle: Int,
        var item: List<BannerEntity>
)

data class ThemeItemDetail(var ptheme_id: Int,
                           var ptheme_bg_img: String,
                           var ptheme_title: String,
                           var ptheme_starttime: String,
                           var ptheme_endtime: String,
                           var ptheme_content: String,
                           var sort: Int,
                           var is_show: String,
                           var productlist: List<GoodsListItem>)

data class GoodsListItem(var product_id: Int,
                         var name: String,
                         var price: String,
                         var image: String)

data class ProadCastDetail(
        var totle: Int,
        var item: List<String>
)

data class HotcategoryBean(var totle:Int
                           ,var item:List<Hotcategory>
)

data class Hotcategory(
    val pic: String,
    val icon: String,
    val id: Int,
    val name: String,
    val pid: Int
)