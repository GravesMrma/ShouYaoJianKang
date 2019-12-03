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
class RebateAddressReq(adress:String,province:Int,city:Int,area:Int) : BaseReq() {
    private var adress = URLEncoder.encode(adress,"UTF-8")
    private var area = area
    private var city = city
    private var disbutor_id = LoginUtils.getRebateId()
    private var province = province
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
        this.adress = URLDecoder.decode(this.adress,"UTF-8")
    }
}
