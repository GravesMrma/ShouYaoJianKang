package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthHabitsBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthHabitsDetailReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthHabitsPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthHabitsView

class HealthHabitsDetailActivity  : BaseMvpActivity<HealthHabitsPresenter>(), HealthHabitsView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onHealthHabitsDetailResult(result: MutableList<HealthHabitsBean>) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_habits_detail)


        initView()

        initData()
    }

    private fun initView(){

    }

    private fun initData(){
        mPresenter.healthHabitsDetailList(HealthHabitsDetailReq(intent.getIntExtra("id",-1)))
    }
}
