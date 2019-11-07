package com.hhjt.baselibrary.rx

import android.util.Log
import com.hhjt.baselibrary.common.ResultCode
import com.hhjt.baselibrary.data.protocol.BaseResp
import io.reactivex.Observable
import io.reactivex.functions.Function

/*
    格式化数据类型转换封装
 */
class BaseFunc<T> : Function<BaseResp<T>, Observable<T>> {
    override fun apply(t: BaseResp<T>): Observable<T> {
        if (t.code == ResultCode.INVALID_TOKEN) {
            return Observable.error(TokenInvalidException(t.msg, t.code))
        } else if (t.code != ResultCode.SUCCESS) {
            return Observable.error(BaseException(t.code, t.msg))
        }

        if (t.data == null) {
            return Observable.error(DataNullException())
        }
        return Observable.just(t.data)
    }
}
