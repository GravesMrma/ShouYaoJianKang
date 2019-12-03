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


//    fun chatRecord(req: NoParamIdReq) {
//        if (!checkNetWork()) {
//            return
//        }
//        mView.showLoading()
//        userServiceImpl.chatRecord(req)
//                .excute(object : BaseSubscriber<ChatBean>(mView) {
//                    override fun onNext(t:ChatBean) {
//                        super.onNext(t)
////                        mView.onChatResult(t)
//                    }
//                }, lifecycleProvider)
//    }
}