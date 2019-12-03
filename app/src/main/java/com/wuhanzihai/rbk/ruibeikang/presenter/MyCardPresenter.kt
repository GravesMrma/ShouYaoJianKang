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

class MyCardPresenter @Inject constructor() : BasePresenter<MyCardView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl


    fun myCard(req:NoParamIdTypeReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.myCard(req)
                .excute(object : BaseSubscriber<MyCardBean>(mView) {
                    override fun onNext(t: MyCardBean) {
                        super.onNext(t)
                        mView.onMyCardResult(t)
                    }
                }, lifecycleProvider)
    }
}