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
import javax.inject.Inject

class DirectPresenter @Inject constructor() : BasePresenter<DirectView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    fun directData(req:DirectReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.directData(req)
                .excute(object : BaseSubscriber<DirectBean>(mView) {
                    override fun onNext(t: DirectBean) {
                        super.onNext(t)
                        mView.onDirectResult(t)
                    }
                }, lifecycleProvider)
    }


    fun inDirectData(req:DirectReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.inDirectData(req)
                .excute(object : BaseSubscriber<DirectBean>(mView) {
                    override fun onNext(t: DirectBean) {
                        super.onNext(t)
                        mView.onInDirectResult(t)
                    }
                }, lifecycleProvider)
    }

    fun myTeam() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.myTeam()
                .excute(object : BaseSubscriber<MyTeamBean>(mView) {
                    override fun onNext(t: MyTeamBean) {
                        super.onNext(t)
                        mView.onMyTeamResult(t)
                    }
                }, lifecycleProvider)
    }
}