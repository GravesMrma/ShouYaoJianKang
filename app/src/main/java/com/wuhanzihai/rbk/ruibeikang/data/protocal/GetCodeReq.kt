package com.wuhanzihai.rbk.ruibeikang.data.protocal

import android.util.Log
import com.hhjt.baselibrary.utils.LoginUtils
import com.wuhanzihai.rbk.ruibeikang.utils.AES

/**
 * Created by wx on 2018/7/10
 */
class GetCodeReq(mobile:String): BaseReq() {
    private var mobile:String = mobile
    private var time:String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()

    init {
        sign = AES.encryptAES(formatParam(this),getKey())
        Log.e("签名是",sign+"//"+time)
    }
}
