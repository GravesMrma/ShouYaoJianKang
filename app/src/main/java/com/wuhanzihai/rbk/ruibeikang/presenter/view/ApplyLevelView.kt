package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface ApplyLevelView : BaseView {

    fun onLevelResult(result: MutableList<RebateLevelBean>){}

    fun onApplyLevelResult(){}

    fun onApplyLevelRecordResult(result:RebateLevelRecordBean){}

    fun onUserInfoResult(result:LoginData){}

    fun onRebateResult(result: RebateBean){}

}