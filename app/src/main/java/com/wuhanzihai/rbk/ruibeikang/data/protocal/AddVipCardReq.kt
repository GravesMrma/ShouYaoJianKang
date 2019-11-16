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
class AddVipCardReq(desc:String, number:Int) : BaseReq() {
    private var desc = URLEncoder.encode(desc,"UTF-8")
    private var disbutor_id = LoginUtils.getRebateId()
    private var number = number
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
        this.desc = URLDecoder.decode(this.desc,"UTF-8")
    }
}
