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
class EditArchivesReq(person_id:Int
                      ,name: String
                      , birthday: String
                      , connections: String
                      , sex: Int
                      ,heights:String
                      ,weights:String
                      ,smoke:String
                      ,drink:String
                      ,msg1:String
                      ,msg2:String
                      ,msg3:String
                      ,msg4:String
                      ,msg5:String
                      ,msg6:String
                      ,msg7:String
                      ,msg8:String
                      ,msg9:String) : BaseReq() {
    private var birthday = birthday
    private var connections = URLEncoder.encode(connections,"UTF-8")
    private var drink = URLEncoder.encode(drink,"UTF-8")
    private var heights = heights
    private var msg1 = URLEncoder.encode(msg1,"UTF-8")
    private var msg2 = URLEncoder.encode(msg2,"UTF-8")
    private var msg3 = URLEncoder.encode(msg3,"UTF-8")
    private var msg4 = URLEncoder.encode(msg4,"UTF-8")
    private var msg5 = URLEncoder.encode(msg5,"UTF-8")
    private var msg6 = URLEncoder.encode(msg6,"UTF-8")
    private var msg7 = URLEncoder.encode(msg7,"UTF-8")
    private var msg8 = URLEncoder.encode(msg8,"UTF-8")
    private var msg9 = URLEncoder.encode(msg9,"UTF-8")
    private var name = URLEncoder.encode(name,"UTF-8")
    private var person_id = person_id
    private var sex = sex
    private var status = 1
    private var smoke = URLEncoder.encode(smoke,"UTF-8")
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()
    private var user_id = LoginUtils.getUserId()
    private var weights = weights

    //    private var img = img

    init {
        sign = AES.encryptAES(formatParam(this), getKey())
        this.connections = URLDecoder.decode(this.connections,"UTF-8")
        this.msg1 = URLDecoder.decode(this.msg1,"UTF-8")
        this.msg2 = URLDecoder.decode(this.msg2,"UTF-8")
        this.msg3 = URLDecoder.decode(this.msg3,"UTF-8")
        this.msg4 = URLDecoder.decode(this.msg4,"UTF-8")
        this.msg5 = URLDecoder.decode(this.msg5,"UTF-8")
        this.msg6 = URLDecoder.decode(this.msg6,"UTF-8")
        this.msg7 = URLDecoder.decode(this.msg7,"UTF-8")
        this.msg8 = URLDecoder.decode(this.msg8,"UTF-8")
        this.msg9 = URLDecoder.decode(this.msg9,"UTF-8")
        this.drink = URLDecoder.decode(this.drink,"UTF-8")
        this.smoke = URLDecoder.decode(this.smoke,"UTF-8")
        this.name = URLDecoder.decode(this.name,"UTF-8")
    }
}
