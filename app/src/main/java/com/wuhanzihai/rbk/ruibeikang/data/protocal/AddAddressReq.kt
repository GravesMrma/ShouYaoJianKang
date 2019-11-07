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
class AddAddressReq(consignee: String
                    , province: String
                    , provincecode: String
                    , city: String
                    , citycode: String
                    , district: String
                    , districtcode: String
                    , address: String
                    , mobile: String
                    ,is_default:Int) : BaseReq() {

    private var address = URLEncoder.encode(address,"UTF-8")
    private var city =  URLEncoder.encode(city,"UTF-8")
    private var citycode = citycode
    private var consignee = URLEncoder.encode(consignee,"UTF-8")
    private var district = URLEncoder.encode(district,"UTF-8")
    private var districtcode = districtcode
    private var is_default = is_default
    private var mobile = mobile
    private var province = URLEncoder.encode(province,"UTF-8")
    private var provincecode = provincecode
    private var time: String = System.currentTimeMillis().toString()
    private var token = LoginUtils.getAuthId()
    private var user_id = GlobalBaseInfo.getBaseInfo()!!.user_id

    init {
        sign = AES.encryptAES(formatParam(this), getKey())

        this.address = URLDecoder.decode(address, "UTF-8")
        this.city = URLDecoder.decode(city, "UTF-8")
        this.consignee = URLDecoder.decode(consignee, "UTF-8")
        this.district = URLDecoder.decode(district, "UTF-8")
        this.province = URLDecoder.decode(province, "UTF-8")
    }
}
