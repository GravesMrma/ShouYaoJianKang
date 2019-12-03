package com.wuhanzihai.rbk.ruibeikang.presenter

import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseData
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import javax.inject.Inject

class ArchivesPresenter @Inject constructor() : BasePresenter<ArchivesView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl

    fun archivesList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.doctorList()
                .excute(object : BaseSubscriber<MutableList<ArchivesBean>>(mView) {
                    override fun onNext(t: MutableList<ArchivesBean>) {
                        super.onNext(t)
                        mView.onArchivesResult(t)
                    }
                }, lifecycleProvider)
    }

    fun createArchives(req:AddArchivesReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.createDoctor(req)
                .excute(object : BaseSubscriber<IsRebateBean>(mView) {
                    override fun onNext(t: IsRebateBean) {
                        super.onNext(t)
                        mView.onAddArchivesResult()
                    }
                }, lifecycleProvider)
    }

    fun deleteArchives(req:DelArchivesReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.deleteDoctor(req)
                .excute(object : BaseSubscriber<BaseData>(mView) {
                    override fun onNext(t: BaseData) {
                        super.onNext(t)
                        mView.onDelArchivesResult()
                    }
                }, lifecycleProvider)
    }


    fun chosePeople(req: ChosePeopleReq ) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.chosePeople(req)
                .excute(object : BaseSubscriber<OrderIdBean>(mView) {
                    override fun onNext(t: OrderIdBean) {
                        super.onNext(t)
                        mView.onChosePeopleResult(t)
                    }
                }, lifecycleProvider)
    }
}