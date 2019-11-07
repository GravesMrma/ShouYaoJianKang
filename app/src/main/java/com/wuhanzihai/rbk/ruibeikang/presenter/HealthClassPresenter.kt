package com.wuhanzihai.rbk.ruibeikang.presenter

import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import javax.inject.Inject

class HealthClassPresenter @Inject constructor() : BasePresenter<HealthClassView>() {

    @Inject
    lateinit var infoServiceImpl: InfoServiceImpl

    fun healthClass(req: NoParamPageReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.healthClass(req)
                .excute(object : BaseSubscriber<HealthClassBean>(mView) {
                    override fun onNext(t: HealthClassBean) {
                        super.onNext(t)
                        mView.onHealthClassResult(t)
                    }
                }, lifecycleProvider)
    }

    fun healthBanner() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.healthBanner()
                .excute(object : BaseSubscriber<HealthClassBannerBean>(mView) {
                    override fun onNext(t: HealthClassBannerBean) {
                        super.onNext(t)
                        mView.onHealthBannerResult(t)
                    }
                }, lifecycleProvider)
    }

    fun healthClassDetail(req: HealthClassDetailReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.healthClassDetail(req)
                .excute(object : BaseSubscriber<HealthClassDetailBean>(mView) {
                    override fun onNext(t: HealthClassDetailBean) {
                        super.onNext(t)
                        mView.onHealthClassDetailResult(t)
                    }
                }, lifecycleProvider)
    }

    fun healthClassDetailMusic(req: HealthClassDetailMusicReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.healthClassDetailMusic(req)
                .excute(object : BaseSubscriber<HealthClassDetailMusicBean>(mView) {
                    override fun onNext(t: HealthClassDetailMusicBean) {
                        super.onNext(t)
                        mView.onHealthClassDetailMusicResult(t)
                    }
                }, lifecycleProvider)
    }
}