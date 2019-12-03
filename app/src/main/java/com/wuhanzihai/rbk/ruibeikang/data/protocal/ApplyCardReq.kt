package com.wuhanzihai.rbk.ruibeikang.data.protocal

import android.util.Log
import com.hhjt.baselibrary.utils.LoginUtils
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * Created by wx on 2018/7/10
 */
class ApplyCardReq( name: String, telpone: String,g_id: Int) : BaseReq() {
    private var agent_id = LoginUtils.getRebateId()
    private var g_id = g_id
    private var name = URLEncoder.encode(name, "UTF-8")
    private var telpone = telpone
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()
    private var user_id = LoginUtils.getUserId()

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
        this.name = URLDecoder.decode(this.name, "UTF-8")
    }
}
