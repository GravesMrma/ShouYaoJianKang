package com.wuhanzihai.rbk.ruibeikang.data.protocal

import android.util.Log
import com.hhjt.baselibrary.utils.LoginUtils
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import java.io.Serializable

/**
 * Created by wx on 2018/7/10
 */
class OrderReq(ordertype: Int, status: Int, search: String,page:Int) : BaseReq(),Serializable {//,coupons: String,actity: String,remark: String
    private var ordertype = ordertype
    private var page = page
    private var search = search
    private var status = status
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()
    private var user_id = LoginUtils.getUserId()

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
    }
}
