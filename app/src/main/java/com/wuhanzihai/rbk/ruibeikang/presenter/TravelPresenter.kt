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

class TravelPresenter @Inject constructor() : BasePresenter<TravelView>() {



    @Inject
    lateinit var mallServiceImpl: MallServiceImpl


    fun singleTravel(req: Int) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        mallServiceImpl.singleTravel(req)
                .excute(object : BaseSubscriber<TravelBean>(mView) {
                    override fun onNext(t: TravelBean) {
                        super.onNext(t)
                        mView.onTravelResult(t)
                    }
                }, lifecycleProvider)
    }
}