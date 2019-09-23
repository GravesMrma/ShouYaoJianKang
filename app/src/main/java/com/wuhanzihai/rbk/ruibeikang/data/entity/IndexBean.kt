package com.wuhanzihai.rbk.ruibeikang.data.entity

data class IndexBean(var item:List<HealthListItem>)

data class IndexBannerBean(var item:List<BannerEntity>)

data class IndexAdvBean(var one:BannerEntity,
                        var two:BannerEntity)

data class IndexClockBean(var titlle:String,
                          var id:String,
                          var cat_id:String,
                          var time:String,
                          var description:String,
                          var content:String
)
