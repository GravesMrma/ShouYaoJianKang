package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface InterrogationView : BaseView {

    fun onCreateQuestion(result:OrderIdBean){}

    fun onPayResult(result: OrderPayBean){}

    fun onDoctorRecordResult(result: InterrogationBean){}

    fun onDelRecordResult(){}

}