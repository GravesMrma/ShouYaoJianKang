package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.MineAdv

interface SetSexView :BaseView {

    fun onSaveInfoResult()

    fun onUserInfoResult(result: LoginData){}

    fun onTagWordsResult(result: MineAdv){}

}