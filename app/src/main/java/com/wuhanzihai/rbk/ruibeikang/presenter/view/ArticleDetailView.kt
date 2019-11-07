package com.wuhanzihai.rbk.ruibeikang.presenter.view

import com.hhjt.baselibrary.presenter.view.BaseView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*

interface ArticleDetailView :BaseView {

    fun onArticleDetailResult(result: ArticleDetailBean)

    fun onColloectResult()

    fun onLikeResult()

}