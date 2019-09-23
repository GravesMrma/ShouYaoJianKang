package com.wuhanzihai.rbk.ruibeikang.data.protocal

import com.wuhanzihai.rbk.ruibeikang.utils.AES

/**
 * Created by wx on 2018/7/10
 */
class LoginReq(mobile:String,code:String): BaseReq() {
    private var code:String = code
    private var mobile:String = mobile
    private var time:String = System.currentTimeMillis().toString()

    init {
        sign = AES.encryptAES(formatParam(this),getKey())
    }
}
