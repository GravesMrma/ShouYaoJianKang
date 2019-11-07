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

class CollectPresenter @Inject constructor() : BasePresenter<CollectView>() {

    @Inject
    lateinit var infoServiceImpl: InfoServiceImpl


    fun collectList(req: CollectListReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        infoServiceImpl.collectList(req)
                .excute(object : BaseSubscriber<MutableList<CollectBean>>(mView) {
                    override fun onNext(t: MutableList<CollectBean>) {
                        super.onNext(t)
                        mView.onCollectResult(t)
                    }
                }, lifecycleProvider)
    }
}