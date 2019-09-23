package com.wuhanzihai.rbk.ruibeikang.common

import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData


/**
 * Created by wx on 2018/8/10
 */
object GlobalBaseInfo {
    private var baseInfo: LoginData? = null

    fun getBaseInfo(): LoginData? {
        return baseInfo
    }

    fun setBaseInfo(baseInfo: LoginData?) {
        GlobalBaseInfo.baseInfo = baseInfo
    }
}