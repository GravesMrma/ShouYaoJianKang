package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.data.entity.DoctorContent
import com.wuhanzihai.rbk.ruibeikang.data.entity.DoctorDetail
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamOrderReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.DoctorPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.DoctorView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_doctor.*
import org.jetbrains.anko.act

class DoctorActivity : BaseMvpActivity<DoctorPresenter>(), DoctorView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onDoctorResult(result: DoctorDetail) {
        ivHead.loadImage(result.image)
        tvName.text = result.name
        tvHospital.text = "(${result.hospital})"
        tvJob.text = result.title + "  " +result.clinic_name  //clinic_name
        val split = result.description.split("   ")
        list.addAll(split)
        adapter.notifyDataChanged()
        tvDesc.text = result.welcome
        tvGoodDesc.text = result.good_at
        tvName.text = result.name
        tvMoney1.text = result.reply_num.toString()
        tvMoney2.text = result.good_rate.toString()
        tvMoney3.text = result.fans_num.toString()
    }

    private lateinit var list: MutableList<String>
    private lateinit var adapter: TagAdapter<String>

    private var orderId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        orderId = intent.getIntExtra("orderId",0)

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : TagAdapter<String>(list) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_doctor_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        rvTag.adapter = adapter
    }

    private fun initData() {
        mPresenter.doctorDetail(NoParamOrderReq(orderId))
    }
}
