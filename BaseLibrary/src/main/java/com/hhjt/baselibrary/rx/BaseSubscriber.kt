package com.hhjt.baselibrary.rx

import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
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
            is BaseException -> baseView.onError(e.msg, e.status)
            is DataNullException -> baseView.onDataIsNull()
            is TokenInvalidException -> {
                baseView.onTokenInvalid(e.msg, e.code)
                LoginUtils.saveLoginStatus(false, "")
                Log.e("跳转页面","是的")
                ARouter.getInstance().build("/rbk/ruibeikang/activity/LoginActivity").navigation()
            }
        }
    }
}
