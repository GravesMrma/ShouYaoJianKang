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
class CommitOrderReq(cartid: String, address_id: Int, coupons: String) : BaseReq() {
    //,coupons: String,actity: String,remark: String
    private var address_id = address_id
    private var coupons = coupons
    var cartid = URLEncoder.encode(cartid, "UTF-8")
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()
    private var user_id = LoginUtils.getUserId()

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
        this.cartid = URLDecoder.decode(this.cartid, "UTF-8")
    }
}
