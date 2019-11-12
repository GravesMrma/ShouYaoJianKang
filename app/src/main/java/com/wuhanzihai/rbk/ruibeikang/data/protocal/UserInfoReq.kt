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
class UserInfoReq(sex: String
                  , birthday: String
                  , head_pic: String
                  , nickname: String
                  , rel_name: String
                  , rel_code: String
                  , height: String
                  , weight: String) : BaseReq() {

    private var birthday: String = URLEncoder.encode(birthday, "UTF-8")
    private var head_pic: String = URLEncoder.encode(head_pic, "UTF-8")
    private var height: String = height
    private var nickname: String = URLEncoder.encode(nickname, "UTF-8")
    private var rel_code: String = rel_code
    private var rel_name: String = URLEncoder.encode(rel_name, "UTF-8")
    private var sex: String = sex
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()
    private var user_id = LoginUtils.getUserId()
    private var weight: String = weight

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
        this.birthday = URLDecoder.decode(this.birthday, "UTF-8")
        this.head_pic = URLDecoder.decode(this.head_pic, "UTF-8")
        this.nickname = URLDecoder.decode(this.nickname, "UTF-8")
        this.rel_name = URLDecoder.decode(this.rel_name, "UTF-8")
    }
}
