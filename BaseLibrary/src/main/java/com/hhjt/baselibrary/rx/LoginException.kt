package com.hhjt.baselibrary.rx

import com.hhjt.baselibrary.rx.BaseData

/*
    定义通用异常
 */
class LoginException(val status: Int, val msg: String) : Throwable()
