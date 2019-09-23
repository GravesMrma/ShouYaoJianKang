package com.hhjt.baselibrary.presenter.view

/*
    MVP中视图回调 基类
 */
interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError(text: String, code: Int)
    fun onDataIsNull()
    fun onTokenInvalid(text: String, code: Int)
}
