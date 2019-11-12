package com.hhjt.baselibrary.rx

import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.JsonIOException
import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.utils.LoginUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/*
    Rx订阅者默认实现
 */
open class BaseSubscriber<T>(val baseView: BaseView, var isHideLoading: Boolean = true) : Observer<T> {

    override fun onSubscribe(p0: Disposable) {
    }

    override fun onComplete() {
        if (isHideLoading) {
            baseView.hideLoading()
        }
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
        baseView.hideLoading()
        when (e) {
            is BaseException -> baseView.onError(e.msg, e.code)
            is DataNullException -> baseView.onDataIsNull()
            is JsonIOException -> baseView.onError("数据格式化错误", 0)
            is TokenInvalidException -> {
                baseView.onTokenInvalid("您的账号已在其他手机登录", e.code)
                LoginUtils.saveLoginStatus(false, "")
                ARouter.getInstance().build("/rbk/ruibeikang/activity/LoginActivity").navigation()
            }
        }
    }
}
