package com.wuhanzihai.rbk.ruibeikang.common

import com.wuhanzihai.rbk.ruibeikang.data.entity.BraceletData

/**
 * Created by wx on 2018/8/10
 */
object BraceletInfo {
    private var braceletData: BraceletData? = null

    fun getBaseInfo(): BraceletData? {
        return braceletData
    }

    fun setBaseInfo(braceletData: BraceletData?) {
        BraceletInfo.braceletData = braceletData
    }
}