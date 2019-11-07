package com.wuhanzihai.rbk.ruibeikang.data.entity.healthData

data class BloodPreData(var time: String
                        , var item: MutableList<BloodPre>)

data class BloodPre(var high: Int
                    , var low: Int
                    , var rate: Int
                    , var state: String
                    , var time: String)