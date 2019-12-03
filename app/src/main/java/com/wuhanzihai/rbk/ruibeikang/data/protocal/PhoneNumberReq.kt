package com.wuhanzihai.rbk.ruibeikang.data.protocal

import android.util.Log
import com.hhjt.baselibrary.utils.LoginUtils
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.utils.AES

/**
 * Created by wx on 2018/7/10
 */
class PhoneNumberReq(disbutor_id:Int,page:Int) : BaseReq() {
    private var disbutor_id = LoginUtils.getRebateId()
    private var page = page
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()

    init {
        if (disbutor_id != 0){
            this.disbutor_id = disbutor_id
        }
        sign = AES.encryptAES(formatParam(this), getKey())
    }
}
