package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface ApplyCashView : BaseView {

    fun onRebateResult(result: RebateBean) {}

    fun onCardResult(result: MyCardBean) {}

    fun onCashDetailResult(result: CashDetailBean) {}

    fun onApplyCashRecordResult(result: ApplyCashRecordBean) {}

    fun onApplyCashResult() {}

    fun onApplyCashListDetail(result: ApplyCashListDetail){}

    fun onApplyCashListResult(result: ApplyCashListBean){}

}