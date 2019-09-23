package com.wuhanzihai.rbk.ruibeikang.data.protocal

import android.util.Log
import com.hhjt.baselibrary.utils.LoginUtils
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.utils.AES

/**
 * Created by wx on 2018/7/10
 */
class UserInfoReq(sex: String
                  , birthday: String
                  , head_pic: String
                  , nickname: String
                  , rel_name: String
                  , rel_code: String
                  , height: String
                  , weight: String) : BaseReq() {

    private var birthday: String = birthday
    private var head_pic: String = head_pic
    private var height: String = height
    private var nickname: String = nickname
    private var rel_code: String = rel_code
    private var rel_name: String = rel_name
    private var sex: String = sex
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()
    private var user_id: String = "2061"
    private var weight: String = weight

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
        Log.e("签名",sign)
    }
}
