package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.text.Html
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.DistributionBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.PhoneNumberBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.PhoneNumberItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.AgrApplyReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.PhoneNumberReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import com.wuhanzihai.rbk.ruibeikang.presenter.DirectDetailPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.PhoneNumberPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.DirectDetailView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.PhoneNumberView
import com.wuhanzihai.rbk.ruibeikang.utils.CityUtils
import kotlinx.android.synthetic.main.activity_direct_detail.*
import org.jetbrains.anko.act

class DirectDetailActivity : BaseMvpActivity<DirectDetailPresenter>(), DirectDetailView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onDirectDetailResult(result: PhoneNumberBean) {
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    private lateinit var adapter: BaseQuickAdapter<PhoneNumberItem, BaseViewHolder>
    private lateinit var list: MutableList<PhoneNumberItem>
    private var page = 1
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direct_detail)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        id = intent.getIntExtra("id",0)

        initView()

        initData()
    }

    private fun initView(){
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<PhoneNumberItem, BaseViewHolder>(R.layout.item_phone_number, list) {
            override fun convert(helper: BaseViewHolder?, item: PhoneNumberItem?) {
                helper!!.getView<RelativeLayout>(R.id.rlViewNum).visibility = View.VISIBLE
                helper.getView<RelativeLayout>(R.id.rlViewBt).visibility = View.GONE
                helper.setText(R.id.tvTime, item!!.create_time)
                        .setText(R.id.tvApplyNumber, "${item.type_name}:${item.name}")
                        .setText(R.id.tvText1, Html.fromHtml("<font color=#08C4AB>注册账号:</font>  <font color=#999999>${item.phone}</font>"))
                        .setText(R.id.tvText2, Html.fromHtml("<font color=#08C4AB>所在区域:</font>  <font color=#999999>${CityUtils.instance.getProvince(act,item.province)} ${CityUtils.instance.getCity(act,item.province,item.city)} ${CityUtils.instance.getArea(act,item.province,item.city,item.area)} </font>"))
                        .setText(R.id.tvText3, Html.fromHtml("<font color=#08C4AB>被分配号段:</font>  <font color=#999999>${item.start_cardno}-${item.end_cardno}</font>"))
                        .setText(R.id.tvYJHNum,"${item.number} 张")
                        .setText(R.id.tvWJHNum,"${item.jihuonumber} 张")
                helper.getView<ConstraintLayout>(R.id.clView).setBackgroundResource(R.drawable.sp_green_10_b0)
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem14_14_14(act))
        adapter.emptyView = getEmptyView(act, "暂无数据")
    }

    private fun initData(){
        mPresenter.phoneNumberDetail(PhoneNumberReq(id,page))
    }
}
