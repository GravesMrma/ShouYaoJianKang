package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.protocal.GetCodeReq
import com.wuhanzihai.rbk.ruibeikang.fragment.HealthyFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.MainFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.MallFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.MineFragment
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerIndexComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.IndexModule
import com.wuhanzihai.rbk.ruibeikang.presenter.MainPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MainView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.act

class MainActivity : BaseMvpActivity<MainPresenter>(),View.OnClickListener,MainView {

    override fun injectComponent() {
        DaggerIndexComponent.builder().activityComponent(mActivityComponent)
                .indexModule(IndexModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUserInfoResult(result: LoginData) {
        GlobalBaseInfo.setBaseInfo(result)
    }

    private var fragment = emptyArray<Fragment>()
    private var last: Int = 0
    private var next: Int = 0

    private val homeFragment by lazy { MainFragment() }
    private val healthyFragment by lazy { HealthyFragment() }
    private val mallFragment by lazy { MallFragment() }
    private val mineFragment by lazy { MineFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StatusBarUtil.setTranslucentForImageView(act, 0, null)

        initView()

        initData()
    }

    private fun initView(){
        rlHome.setOnClickListener(this)
        rlHeath.setOnClickListener(this)
        rlOil.setOnClickListener(this)
        rlCenter.setOnClickListener(this)

    }

    private fun initData(){

        fragment = arrayOf(homeFragment, healthyFragment, mallFragment, mineFragment)
        var ft: FragmentTransaction = supportFragmentManager.beginTransaction().add(R.id.fView, fragment[0])
        ft.commit()
        upButton(0)

//        Bus.observe<IndexJumpFragment>().subscribe {
//            jumpFragment(it.index)
//        }.registerInBus(this)

        mPresenter.getUserInfo()
    }

    override fun onClick(p0: View?) {

        when (p0!!.id) {
            R.id.rlHome -> {
                next = 0
            }
            R.id.rlHeath -> {
                next = 1
            }
            R.id.rlOil -> {
                next = 2
            }
            R.id.rlCenter -> {
                next = 3
            }
        }
        jumpFragment(next)
    }


    private fun jumpFragment(next: Int) {
        if (last != next) {
            var ft: FragmentTransaction = supportFragmentManager.beginTransaction().hide(fragment[last])
            if (!fragment[next].isAdded) {
                ft.add(R.id.fView, fragment[next])
            }
            ft.show(fragment[next]).commitAllowingStateLoss()
            upButton(next)
        }
        last = next
    }


    private fun upButton(int: Int) {
        tvHome.isSelected = false
        tvHeath.isSelected = false
        tvOil.isSelected = false
        tvCenter.isSelected = false
        when (int) {
            0 -> {
                tvHome.isSelected = true
            }
            1 -> {
                tvHeath.isSelected = true
            }
            2 -> {
                tvOil.isSelected = true
            }
            3 -> {
                tvCenter.isSelected = true
            }
        }
    }
}
