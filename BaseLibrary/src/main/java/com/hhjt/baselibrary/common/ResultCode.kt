package com.hhjt.baselibrary.common

/*
    网络响应码
 */
class ResultCode {
    companion object {
        const val SUCCESS = 1
        const val INVALID_PARAM = 406
        const val INVALID_SIGN = 412
        const val INVALID_TOKEN = 401
        const val ERROR = 400
    }
}
