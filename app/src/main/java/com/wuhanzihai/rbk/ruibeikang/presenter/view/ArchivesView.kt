package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface ArchivesView :BaseView {

    fun onArchivesResult(result: MutableList<ArchivesBean>){}

    fun onAddArchivesResult(){}

    fun onEditArchivesResult(){}

    fun onDelArchivesResult(){}

    fun onChosePeopleResult(result:OrderIdBean){}

    fun onArchivesDetail(result: ArchivesDetail){}

}