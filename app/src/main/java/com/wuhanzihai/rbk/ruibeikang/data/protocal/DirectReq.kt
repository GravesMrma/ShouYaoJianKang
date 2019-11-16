package com.wuhanzihai.rbk.ruibeikang.data.protocal

import android.util.Log
import com.hhjt.baselibrary.utils.LoginUtils
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.utils.AES

/**
 * Created by wx on 2018/7/10
 */
class DirectReq(page:Int,day:Int) : BaseReq() {
    private var day = day
    private var disbutor_id = LoginUtils.getRebateId()
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()
    private var page = page

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
    }
}
