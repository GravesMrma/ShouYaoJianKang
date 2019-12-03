package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface BankCardView : BaseView {

    fun onBankCardResult(result: MyCardBean){}

    fun onAddBankCardResult(){}

    fun onDelBankCardResult(){}

}