package com.wuhanzihai.rbk.ruibeikang.data.entity

import java.io.Serializable

data class MallBean(var banner:banner,
                    var tjcategory:tjcategory,
                    var coupon:coupon,
                    var newpeople:newpeople,
                    var theme:List<themeItem>,
                    var proadcast:proadcast): Serializable

data class banner(
        var totle: Int,
        var item: List<BannerEntity>)

data class tjcategory(
        var totle: Int,
        var item: List<tjcategoryItem>
) : Serializable

data class tjcategoryItem(var id: Int,
                          var name: String,
                          var icon: String)

data class coupon(
        var totle: Int,
        var item: List<BannerEntity>
)

data class newpeople(
        var totle: Int,
        var item: List<BannerEntity>
)

data class themeItem(var ptheme_id: Int,
                     var ptheme_bg_img: String,
                     var ptheme_title: String,
                     var ptheme_starttime: String,
                     var ptheme_endtime: String,
                     var ptheme_content: String,
                     var sort: Int,
                     var is_show: String,
                     var productlist: List<productlistItem>)

data class productlistItem(var product_id: Int,
                           var name: String,
                           var price: String,
                           var image: String)

data class proadcast(
        var totle: Int,
        var item: List<String>
)