package com.wuhanzihai.rbk.ruibeikang.data.entity.healthData

data class BloodSugarData(var time: String
                          , var item: MutableList<BloodSugar>)

data class BloodSugar(var value: Double
                      , var type: String
                      , var state: String
                      , var time: String)