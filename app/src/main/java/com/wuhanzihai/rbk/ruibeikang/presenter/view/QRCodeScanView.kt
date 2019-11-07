package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData

interface QRCodeScanView :BaseView {

    fun onApplyResult(result: Int)

    fun onIsBeginResult(result: String)

    fun onJoinResult()

    fun onIsApplyResult(result: String)

}