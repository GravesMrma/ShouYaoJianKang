package com.hhjt.baselibrary.rx

/*
    Rx2数据为空不能直接发射
 */
class TokenInvalidException(val msg: String, val code: Int) : Throwable()
