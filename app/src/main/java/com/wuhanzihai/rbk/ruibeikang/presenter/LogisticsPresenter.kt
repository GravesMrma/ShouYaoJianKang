package com.wuhanzihai.rbk.ruibeikang.presenter

import com.google.gson.Gson
import com.wuhanzihai.rbk.ruibeikang.presenter.view.LoginView
import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.Logistics
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GetCodeReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LoginReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LogisticsReq
import com.wuhanzihai.rbk.ruibeikang.presenter.view.LogisticsView
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import com.wuhanzihai.rbk.ruibeikang.utils.MD5Util
import javax.inject.Inject

class LogisticsPresenter @Inject constructor() : BasePresenter<LogisticsView>()  {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    fun logistics(req: LogisticsReq){
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.logistics(req)
                .excute(object : BaseSubscriber<Logistics>(mView) {
                    override fun onNext(t: Logistics) {
                        super.onNext(t)
                        mView.onLogisticsResult(t)
                    }
                }, lifecycleProvider)
    }


}