package com.hhjt.baselibrary.rx

import com.hhjt.baselibrary.common.ResultCode
import io.reactivex.Observable
import io.reactivex.functions.Function

/*
    原数据类型转换封装
 */
class BaseFuncT<T : BaseData> : Function<T, Observable<T>> {
    override fun apply(t: T): Observable<T> {
        return when (t.code) {
            ResultCode.SUCCESS -> Observable.just(t)
            ResultCode.INVALID_TOKEN -> Observable.error(TokenInvalidException(t.msg, t.code))
            else -> Observable.error(BaseException(t.code, t.msg))
        }
    }
}
