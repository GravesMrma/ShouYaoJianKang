package com.wuhanzihai.rbk.ruibeikang.presenter

import com.google.gson.Gson
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class HealthHabitsPresenter @Inject constructor() : BasePresenter<HealthHabitsView>() {

    @Inject
    lateinit var infoServiceImpl: InfoServiceImpl

    fun healthHabitsList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.healthHabitsList()
                .excute(object : BaseSubscriber<MutableList<HealthHabitsBean>>(mView) {
                    override fun onNext(t: MutableList<HealthHabitsBean>) {
                        super.onNext(t)
                        mView.onHealthHabitsResult(t)
                    }
                }, lifecycleProvider)
    }

    fun healthHabitsDetailList(healthHabitsDetailReq: HealthHabitsDetailReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.healthHabitsDetailList(healthHabitsDetailReq)
                .excute(object : BaseSubscriber<MutableList<HealthHabitsBean>>(mView) {
                    override fun onNext(t: MutableList<HealthHabitsBean>) {
                        super.onNext(t)
//                        mView.
                    }
                }, lifecycleProvider)
    }

}