package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.protocal.UserInfoReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.SetSexPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SetSexView
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomDatePicker
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomSinglePicker
import kotlinx.android.synthetic.main.activity_set_sex.*
import org.jetbrains.anko.act
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
        startActivity<SetTagActivity>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_sex)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

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
            }).setData(weight).setIsLoop(false).show()
        }

        tvHeight.onClick {
            CustomSinglePicker(this, CustomSinglePicker.ResultHandler {
                height1 = it
                tvHeight.text = it
            }).setData(height).setIsLoop(false).show()
        }
        btCommit.onClick {
            mPresenter.saveInfo(UserInfoReq(sex, birthday, "http://www.hcjiankang.com/androidimg/mid_icon_shuaige_s.png", "", "", "", "180", "65"))
        }
    }

    private fun initData() {
        weight = mutableListOf()
        for (i in 30..200) {
            weight.add("${i}Kg")
        }
        height = mutableListOf()
        for (i in 100..250) {
            height.add("${i}cm")
        }
    }
}
