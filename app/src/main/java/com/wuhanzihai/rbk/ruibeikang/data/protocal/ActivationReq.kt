package com.wuhanzihai.rbk.ruibeikang.data.protocal

import android.util.Log
import com.hhjt.baselibrary.utils.LoginUtils
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.utils.AES

/**
 * Created by wx on 2018/7/10
 */
class ActivationReq(card_no: String, cardpwd: String) : BaseReq() {
    private var card_no = card_no
    private var cardpwd = cardpwd
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()
    private var user_id = LoginUtils.getUserId()

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
    }
}
