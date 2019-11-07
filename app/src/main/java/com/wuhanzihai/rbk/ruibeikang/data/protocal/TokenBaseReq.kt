package com.wuhanzihai.rbk.ruibeikang.data.protocal

import com.hhjt.baselibrary.utils.LoginUtils

open class TokenBaseReq {
    private var token = LoginUtils.getAuthId()

}