package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.protocal.UserInfoReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.SetSexPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SetSexView
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomDatePicker
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomSinglePicker
import kotlinx.android.synthetic.main.activity_set_sex.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SetSexActivity : BaseMvpActivity<SetSexPresenter>(), SetSexView {
    private lateinit var weight: MutableList<String>
    private lateinit var height: MutableList<String>

    private var sex = ""  //1  man  2 woman
    private var birthday = ""
    private var weight1 = ""
    private var height1 = ""

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onSaveInfoResult() {
        toast("修改成功")
        startActivity<MainActivity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_sex)

        initView()

        initData()
    }

    private fun initView() {
        tvMan.onClick {
            tvWoman.isSelected = false
            tvMan.isSelected = true
            sex = "1"
        }
        tvWoman.onClick {
            tvWoman.isSelected = true
            tvMan.isSelected = false
            sex = "2"
        }
        tvAge.onClick {
            CustomDatePicker.getInstance(this) {
                birthday = it
                tvAge.text = it
            }.show()
        }

        tvWeight.onClick {
            CustomSinglePicker(this, CustomSinglePicker.ResultHandler {
                weight1 = it
                tvWeight.text = it
            }).show(weight,false)
        }

        tvHeight.onClick {
            CustomSinglePicker(this, CustomSinglePicker.ResultHandler {
                height1 = it
                tvHeight.text = it
            }).show(height,false)
        }
        btCommit.onClick {
            mPresenter.sendCode(UserInfoReq(sex, birthday, "", "", "", "", "180", "65"))
        }
    }

    private fun initData() {
        weight = mutableListOf()
        for (i in 40..100) {
            weight.add("${i}Kg")
        }
        height = mutableListOf()
        for (i in 100..200) {
            height.add("${i}cm")
        }
    }
}
