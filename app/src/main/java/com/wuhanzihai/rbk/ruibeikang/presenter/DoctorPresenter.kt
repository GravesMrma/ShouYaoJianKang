package com.wuhanzihai.rbk.ruibeikang.presenter

import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import javax.inject.Inject

class DoctorPresenter @Inject constructor() : BasePresenter<DoctorView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl


    fun doctorDetail(req: NoParamOrderReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.doctorDetail(req)
                .excute(object : BaseSubscriber<DoctorDetail>(mView) {
                    override fun onNext(t:DoctorDetail) {
                        super.onNext(t)
                        mView.onDoctorResult(t)
                    }
                }, lifecycleProvider)
    }
}