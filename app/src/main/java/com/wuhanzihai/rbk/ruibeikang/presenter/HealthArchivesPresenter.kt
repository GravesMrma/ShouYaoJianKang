package com.wuhanzihai.rbk.ruibeikang.presenter

import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import javax.inject.Inject

class HealthArchivesPresenter @Inject constructor() : BasePresenter<HealthArchivesView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

//    fun healthClass(req: NoParamPageReq) {
//        if (!checkNetWork()) {
//            return
//        }
//        mView.showLoading()
//        infoServiceImpl.healthClass(req)
//                .excute(object : BaseSubscriber<HealthClassBean>(mView) {
//                    override fun onNext(t: HealthClassBean) {
//                        super.onNext(t)
//                        mView.onHealthClassResult(t)
//                    }
//                }, lifecycleProvider)
//    }


}