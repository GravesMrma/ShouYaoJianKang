package com.wuhanzihai.rbk.ruibeikang.presenter

import com.wuhanzihai.rbk.ruibeikang.service.impl.UserServiceImpl
import com.hhjt.baselibrary.ext.excute
import com.hhjt.baselibrary.presenter.BasePresenter
import com.hhjt.baselibrary.rx.BaseSubscriber
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.*
import com.wuhanzihai.rbk.ruibeikang.presenter.view.*
import com.wuhanzihai.rbk.ruibeikang.service.impl.InfoServiceImpl
import java.io.File
import javax.inject.Inject

class ChatRoomPresenter @Inject constructor() : BasePresenter<ChatRoomView>() {

    @Inject
    lateinit var userServiceImpl: UserServiceImpl


    fun chatRecord(req: ChatRecordReq) {
        if (!checkNetWork()) {
            return
        }
//        mView.showLoading()
        userServiceImpl.chatRecord(req)
                .excute(object : BaseSubscriber<ChatBean>(mView) {
                    override fun onNext(t:ChatBean) {
                        super.onNext(t)
                        mView.onChatResult(t)
                    }
                }, lifecycleProvider)
    }

    fun addQuestion(fileReq: MutableList<File>, req: AddQuestionReq) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        userServiceImpl.addQuestion(fileReq,req)
                .excute(object : BaseSubscriber<IsRebateBean>(mView) {
                    override fun onNext(t:IsRebateBean) {
                        super.onNext(t)
                        mView.onSendSuccess()
                    }
                }, lifecycleProvider)
    }
}