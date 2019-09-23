package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData

interface LoginView :BaseView {

    fun onGetCode()

    fun onLoginResult(result: LoginData)

}