package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.protocal.UserInfoReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.SetSexPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SetSexView
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomDatePicker
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomSinglePicker
import kotlinx.android.synthetic.main.activity_edit_user_info.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class EditUserInfoActivity : BaseMvpActivity<SetSexPresenter>(), SetSexView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onSaveInfoResult() {
        showTextDesc(act,"修改成功")
    }

    override fun onUserInfoResult(result: LoginData) {
        GlobalBaseInfo.setBaseInfo(result)
//        ivHead.loadImage(result.head_pic)
        if (result.sex == 1) {
            ivHead.loadImage("http://www.hcjiankang.com/androidimg/mid_icon_shuaige_s.png")
        } else {
            ivHead.loadImage("http://www.hcjiankang.com/androidimg/mid_icon_meinv_s.png")
        }
        edName.setText(result.nickname)
        tvBirthday.text = result.birthday
        sex = result.sex
        when (result.sex) {
            1 -> tvSex.text = "男"
            2 -> tvSex.text = "女"
        }
        tvHeight.text = "${result.height}cm"
        tvWeight.text = "${result.weight}Kg"
    }

    private val sexs = mutableListOf("男", "女")
    private var sex = 0
    private var weight1 = ""
    private var height1 = ""

    private lateinit var weight: MutableList<String>
    private lateinit var height: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_info)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        tvTitle.setMoreTextAction {
            var headUrl = ""
            if (sex == 1) {
                headUrl = "http://www.hcjiankang.com/androidimg/mid_icon_shuaige_s.png"
            }
            if (sex == 2) {
                headUrl = "http://www.hcjiankang.com/androidimg/mid_icon_meinv_s.png"
            }
            mPresenter.saveInfo(UserInfoReq(sex.toString(),
                    tvBirthday.text.toString(),
                    headUrl,
                    edName.text.toString(),
                    "",
                    ""
                    , height1.replace("cm", "")
                    , weight1.replace("Kg", "")))
        }

        rlSex.onClick {
            CustomSinglePicker(act) {
                tvSex.text = it
                when (it) {
                    "男" -> {
                        sex = 1
                    }
                    "女" -> {
                        sex = 2
                    }
                }
            }.setData(sexs).setIsLoop(false).show()
        }
        rlBirthday.onClick {
            CustomDatePicker(act) {
                tvBirthday.text = it
            }.setIsLoop(false).showSpecificTime(false).show()
        }

        rlWeight.onClick {
            CustomSinglePicker(this, CustomSinglePicker.ResultHandler {
                weight1 = it
                tvWeight.text = it
            }).setData(weight).setIsLoop(false).show()
        }

        rlHeight.onClick {
            CustomSinglePicker(this, CustomSinglePicker.ResultHandler {
                height1 = it
                tvHeight.text = it
            }).setData(height).setIsLoop(false).show()
        }
        rlTag.onClick {
            startActivity<SetTagActivity>()
        }
    }

    private fun initData() {
        mPresenter.getUserInfo()
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
