package com.wuhanzihai.rbk.ruibeikang.data.protocal

import android.util.Log
import com.hhjt.baselibrary.utils.LoginUtils
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.utils.AES

/**
 * Created by wx on 2018/7/10
 */
class HealthClassDetailReq(mc_id: Int) : BaseReq() {
    private var mc_id = mc_id
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
    }
}
