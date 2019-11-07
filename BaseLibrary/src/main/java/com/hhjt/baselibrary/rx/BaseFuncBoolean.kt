package com.hhjt.baselibrary.rx

import com.hhjt.baselibrary.common.ResultCode
import com.hhjt.baselibrary.data.protocol.BaseResp
import io.reactivex.Observable
import io.reactivex.functions.Function

/*
    Boolean类型转换封装
 */
class BaseFuncBoolean<T> : Function<BaseResp<T>, Observable<Boolean>> {
    override fun apply(t: BaseResp<T>): Observable<Boolean> {
        if (t.code != ResultCode.SUCCESS) {
            return Observable.error(BaseException(t.code, t.msg))
        } else if (t.code == ResultCode.INVALID_TOKEN) {
            return Observable.error(TokenInvalidException(t.msg, t.code))
        }
        return Observable.just(true)
    }
}
