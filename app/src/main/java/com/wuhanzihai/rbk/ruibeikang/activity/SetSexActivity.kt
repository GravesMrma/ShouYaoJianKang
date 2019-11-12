package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.hhjt.baselibrary.utils.LoginUtils
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
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
        finish()
    }

    override fun onUserInfoResult(result: LoginData) {

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
            CustomDatePicker(this) {
                birthday = it
                tvAge.text = it
            }.setIsLoop(false).showSpecificTime(false).show()
        }

        tvWeight.onClick {
            CustomSinglePicker(this, CustomSinglePicker.ResultHandler {
                weight1 = it
                tvWeight.text = it
            }).setData(weight).setSelected(weight.size / 3).setIsLoop(false).show()
        }

        tvHeight.onClick {
            CustomSinglePicker(this, CustomSinglePicker.ResultHandler {
                height1 = it
                tvHeight.text = it
            }).setData(height).setSelected(height.size / 2).setIsLoop(false).show()
        }
        btCommit.onClick {
            if (sex.isEmpty() ||
                    birthday.isEmpty() ||
                    height1.isEmpty() ||
                    weight1.isEmpty()) {
                toast("请填写完整信息")
                return@onClick
            }
            var headUrl = ""
            if (sex == "1") {
                headUrl = "http://www.hcjiankang.com/androidimg/mid_icon_shuaige_s.png"
            }
            if (sex == "2") {
                headUrl = "http://www.hcjiankang.com/androidimg/mid_icon_meinv_s.png"
            }

            mPresenter.saveInfo(UserInfoReq(sex, birthday,
                    headUrl,
                    "",
                    "",
                    "",
                    height1.replace("cm", ""),
                    weight1.replace("Kg", "")))
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
