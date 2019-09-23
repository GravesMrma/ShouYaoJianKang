package com.wuhanzihai.rbk.ruibeikang.presenter

import com.google.gson.Gson
import com.wuhanzihai.rbk.ruibeikang.presenter.view.LoginView
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GetCodeReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LoginReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.TimeReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.UserInfoReq
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MainView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MallView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SetSexView
import com.wuhanzihai.rbk.ruibeikang.service.impl.IndexServiceImpl
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class MainFragmentPresenter @Inject constructor() : BasePresenter<MainView>() {

    @Inject
    lateinit var indexServiceImpl: IndexServiceImpl

    fun indexInfo() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        indexServiceImpl.indexInfo()
                .excute(object : BaseSubscriber<IndexBean>(mView) {
                    override fun onNext(t: IndexBean) {
                        super.onNext(t)
                        mView.onIndexDate(t)
                    }
                }, lifecycleProvider)
    }

    fun indexAdv() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        indexServiceImpl.indexAdv()
                .excute(object : BaseSubscriber<IndexAdvBean>(mView) {
                    override fun onNext(t: IndexAdvBean) {
                        super.onNext(t)
                        mView.onIndexAdv(t)
                    }
                }, lifecycleProvider)
    }

    fun indexBanner() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        indexServiceImpl.indexBanner()
                .excute(object : BaseSubscriber<IndexBannerBean>(mView) {
                    override fun onNext(t: IndexBannerBean) {
                        super.onNext(t)
                        mView.onIndexBanner(t)
                    }
                }, lifecycleProvider)
    }

    fun indexClock() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        indexServiceImpl.indexClock()
                .excute(object : BaseSubscriber<IndexClockBean>(mView) {
                    override fun onNext(t: IndexClockBean) {
                        super.onNext(t)
                        mView.onIndexClock(t)
                    }
                }, lifecycleProvider)
    }

}