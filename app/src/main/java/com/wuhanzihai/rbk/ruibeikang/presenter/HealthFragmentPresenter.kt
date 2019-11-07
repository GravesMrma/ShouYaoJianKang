package com.wuhanzihai.rbk.ruibeikang.presenter

import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import javax.inject.Inject

class HealthFragmentPresenter @Inject constructor() : BasePresenter<HealthFragmentView>() {

    @Inject
    lateinit var infoServiceImpl: InfoServiceImpl

    fun healthInfoClass() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.healthInfoClass()
                .excute(object : BaseSubscriber<MutableList<HealthTitleBean>>(mView) {
                    override fun onNext(t: MutableList<HealthTitleBean>) {
                        super.onNext(t)
                    }
                }, lifecycleProvider)
    }

    fun healthIndex() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.healthIndex()
                .excute(object : BaseSubscriber<HealthIndexBean>(mView) {
                    override fun onNext(t: HealthIndexBean) {
                        super.onNext(t)
                        mView.onHealthResult(t)
                    }
                }, lifecycleProvider)
    }
}