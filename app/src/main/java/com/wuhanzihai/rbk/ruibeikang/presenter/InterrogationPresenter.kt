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
import com.wuhanzihai.rbk.ruibeikang.service.impl.MallServiceImpl
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import java.io.File
import javax.inject.Inject

class InterrogationPresenter @Inject constructor() : BasePresenter<InterrogationView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl


    fun createQuestion(fileReq: MutableList<File>,req: CreateDoctorReq ) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.createQuestion(fileReq,req)
                .excute(object : BaseSubscriber<OrderIdBean>(mView) {
                    override fun onNext(t: OrderIdBean) {
                        super.onNext(t)
                        mView.onCreateQuestion(t)
                    }
                }, lifecycleProvider)
    }

    fun payInterrogation(req: PayInterrogationReq ) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.payInterrogation(req)
                .excute(object : BaseSubscriber<OrderPayBean>(mView) {
                    override fun onNext(t: OrderPayBean) {
                        super.onNext(t)
                        mView.onPayResult(t)
                    }
                }, lifecycleProvider)
    }


    fun doctorRecord() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.doctorRecord(NoParamIdReq())
                .excute(object : BaseSubscriber<InterrogationBean>(mView) {
                    override fun onNext(t: InterrogationBean) {
                        super.onNext(t)
                        mView.onDoctorRecordResult(t)
                    }
                }, lifecycleProvider)
    }


    fun deleteRecord(req:NoParamOrderIdReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.deleteRecord(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onDelRecordResult()
                    }
                }, lifecycleProvider)
    }
}