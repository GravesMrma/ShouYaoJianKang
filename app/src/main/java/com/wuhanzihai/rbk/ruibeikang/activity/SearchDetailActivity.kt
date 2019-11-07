package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsResult
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthClassBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthListBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.SearchBean
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.presenter.SearchPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SearchView

class SearchDetailActivity : BaseMvpActivity<SearchPresenter>(), SearchView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onSearchBean(result: SearchBean) {

    }

    override fun onSearchGoodsBean(result: GoodsResult) {

    }

    override fun onHealthListResult(result: HealthListBean) {

    }

    override fun onHealthClassResult(result: HealthClassBean) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_detail)

        initView()

        initData()
    }

    private fun initView(){

    }

    private fun initData(){

    }

}
