package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.ArchivesBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderIdBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.AddArchivesReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.ArchivesPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ArchivesView
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomDatePicker
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomSinglePicker
import kotlinx.android.synthetic.main.activity_add_archives.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class AddArchivesActivity : BaseMvpActivity<ArchivesPresenter>(), ArchivesView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onAddArchivesResult() {
        setResult(4321)
        showTextDesc(act,"添加成功"){
            finish()
        }
    }

    private var sex = 1
    private var birthday = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_archives)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        mRgRecord.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.mRbMan -> {
                    sex = 1
                }
                R.id.mRbWoman -> {
                    sex = 2
                }
            }
        }
        tvCommit.onClick {
            if (edName.text.isEmpty()){
                toast("请填写姓名")
                return@onClick
            }
            if (tvGuanxi.text.isEmpty()){
                toast("请选择与您的关系")
                return@onClick
            }
            if (birthday.isEmpty()){
                toast("请填写出生日期")
                return@onClick
            }

            mPresenter.createArchives(AddArchivesReq(edName.text.toString(),
                    birthday,
                    tvGuanxi.text.toString(),
                    sex))
        }

        rlGuanxi.onClick {
            CustomSinglePicker(act) {
                tvGuanxi.text = it
            }.setData(mutableListOf("本人", "妻子", "父亲", "母亲", "儿子", "女儿")).setSelected(0).setIsLoop(false).show()
        }

        rlBirthday.onClick {
            CustomDatePicker(act) {
                birthday = it
                tvBirthday.text = it
            }.setIsLoop(false).showSpecificTime(false).show()
        }
    }

    private fun initData() {

    }
}
