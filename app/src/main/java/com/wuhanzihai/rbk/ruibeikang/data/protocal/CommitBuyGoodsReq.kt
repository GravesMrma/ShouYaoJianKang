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
class CommitBuyGoodsReq(product_id: Int, sku_id: Int,address_id: Int,postage: Int,remark:String,coupons:String,number:String) : BaseReq() {
    private var address_id = address_id
    private var coupons = coupons
    private var number = number
    private var postage = postage
    private var product_id = product_id
    private var remark = remark
    private var sku_id = sku_id
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()
    private var user_id = LoginUtils.getUserId()

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
    }
}
