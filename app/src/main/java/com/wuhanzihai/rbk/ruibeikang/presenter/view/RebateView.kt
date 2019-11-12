package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.hhjt.baselibrary.rx.BaseData
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface RebateView : BaseView {

    fun onRebateResult(result: RebateBean)

    fun onUserInfoResult(result: LoginData)

}