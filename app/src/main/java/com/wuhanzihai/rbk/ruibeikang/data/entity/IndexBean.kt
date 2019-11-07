package com.wuhanzihai.rbk.ruibeikang.data.entity

data class IndexBean(var item: List<HealthListItem>)

data class IndexBannerBean(var item: List<BannerEntity>)

data class IndexAdvBean(var one: BannerEntity,
                        var two: BannerEntity,
                        var theme: BannerEntity)

data class IndexClockBean(var titlle: String,
                          var id: Int,
                          var cat_id: Int,
                          var time: String,
                          var endtime: String,
                          var description: String,
                          var content: String
)
