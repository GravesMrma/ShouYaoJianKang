package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface PhoneNumberView : BaseView {

    fun onAddCardResult()

    fun onAgrApplyResult()

    fun onPhoneListResult(result:PhoneNumberBean)

    fun onDistributionResult(result:DistributionBean)

}