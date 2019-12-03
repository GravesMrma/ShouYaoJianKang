package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.DistributionBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.PhoneNumberBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.PhoneNumberItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.AddVipCardReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.AgrApplyReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamPageReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.PhoneNumberReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import com.wuhanzihai.rbk.ruibeikang.presenter.PhoneNumberPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.PhoneNumberView
import kotlinx.android.synthetic.main.fragment_phone_number.*
import org.jetbrains.anko.act
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.toast
import per.goweii.anylayer.AnyLayer

@SuppressLint("ValidFragment")
class PhoneNumberFragment(var type: Int) : BaseMvpFragment<PhoneNumberPresenter>(), PhoneNumberView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onAddCardResult() {
        showTextDesc(act, "提交成功,等待上级确认")
        page == 1
        list.clear()
        initData()
    }

    override fun onAgrApplyResult() {
        page == 1
        list.clear()
        initData()
        showTextDesc(act, "审核成功等待厂家发货给审核者")
    }

    override fun onPhoneListResult(result: PhoneNumberBean) {
        srView.finish()
        tvAllNumber.text = "申请 ${result.totle}张"
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    override fun onDistributionResult(result: DistributionBean) {
        srView.finish()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    private val dialog by lazy {
        val anyLayer =
                AnyLayer.with(act)
                        .contentView(R.layout.layout_apply_phone)
                        .gravity(Gravity.BOTTOM)
                        .backgroundResource(R.color.clarity_40)
                        .onClick(R.id.tvCommit) { anyLayer, v ->
                            addVipCard()
                            anyLayer.dismiss()
                        }
                        .onClick(R.id.ivClose) { anyLayer, v ->
                            anyLayer.dismiss()
                        }
        anyLayer
    }

    private lateinit var adapter: BaseQuickAdapter<PhoneNumberItem, BaseViewHolder>
    private lateinit var list: MutableList<PhoneNumberItem>
    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_phone_number, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<PhoneNumberItem, BaseViewHolder>(R.layout.item_phone_number, list) {
            override fun convert(helper: BaseViewHolder?, item: PhoneNumberItem?) {
                if (type == 1) {
                    helper!!.getView<RelativeLayout>(R.id.rlViewNum).visibility = View.GONE
                    when (item!!.apply_status) {
                        0 -> { //  审核中
                            helper.setText(R.id.tvTime, item!!.create_time)
                                    .setText(R.id.tvApplyNumber, "申请  ${item.number}张")
                                    .setText(R.id.tvText1, Html.fromHtml("<font color=#08C4AB>审核方:</font>  <font color=#999999>${item.topname}</font>"))
                            helper.getView<TextView>(R.id.tvText2).visibility = View.GONE
                            helper.getView<TextView>(R.id.tvText3).visibility = View.GONE
                            helper.getView<RelativeLayout>(R.id.rlViewNum).visibility = View.GONE
                            helper.getView<RelativeLayout>(R.id.rlViewBt).visibility = View.VISIBLE

                            helper.getView<ConstraintLayout>(R.id.clView).setBackgroundResource(R.drawable.sp_green_10_b0)
                            helper.getView<TextView>(R.id.tvCommit).visibility = View.GONE
                            helper.setText(R.id.tvState, "平台审核中...")
                        }
                        1 -> { // 通过
                            helper.setText(R.id.tvTime, item!!.create_time)
                                    .setText(R.id.tvApplyNumber, "申请人:${item.topname}(守护者)")
                                    .setText(R.id.tvText1, Html.fromHtml("<font color=#08C4AB>审核方:</font>  <font color=#999999>${item.topname}</font>"))
                                    .setText(R.id.tvText2, Html.fromHtml("<font color=#08C4AB>号段:</font>  <font color=#999999>${item.start_cardno}-${item.end_cardno}</font>"))
                                    .setText(R.id.tvText3, Html.fromHtml("<font color=#08C4AB>快递单号:</font>  <font color=#999999>未知数据</font>"))
                                    .setText(R.id.tvYJHNum, Html.fromHtml("<font color=#999999>${item.number - item.jihuonumber} 张</font>"))
                                    .setText(R.id.tvWJHNum, Html.fromHtml("<font color=#999999>${item.number} 张</font>"))
                            helper.getView<TextView>(R.id.tvText2).visibility = View.VISIBLE
                            helper.getView<TextView>(R.id.tvText3).visibility = View.VISIBLE
                            helper.getView<RelativeLayout>(R.id.rlViewNum).visibility = View.VISIBLE
                            helper.getView<RelativeLayout>(R.id.rlViewBt).visibility = View.GONE
                            helper.getView<ConstraintLayout>(R.id.clView).setBackgroundResource(R.drawable.sp_green_10_b0)
                        }
                        2 -> { // 不通过
                            helper.setText(R.id.tvTime, item.create_time + "审核驳回")
                                    .setText(R.id.tvApplyNumber, "申请人:${item.topname}(守护者)")
                                    .setText(R.id.tvText1, Html.fromHtml("<font color=#08C4AB>审核方:</font>  <font color=#999999>${item.topname}</font>"))
                            helper.getView<TextView>(R.id.tvText2).visibility = View.GONE
                            helper.getView<TextView>(R.id.tvText3).visibility = View.GONE
                            helper.getView<RelativeLayout>(R.id.rlViewNum).visibility = View.GONE
                            helper.getView<RelativeLayout>(R.id.rlViewBt).visibility = View.VISIBLE
                            helper.getView<ConstraintLayout>(R.id.clView).setBackgroundResource(R.drawable.sp_orange_10_b0)
                            helper.setText(R.id.tvState, "审核不通过")
//                        helper.setText(R.id.tvState,item.start_rmark)
                        }
                    }
                }
                if (type == 2) {
                    helper!!.getView<RelativeLayout>(R.id.rlViewNum).visibility = View.GONE
                    helper.getView<RelativeLayout>(R.id.rlViewBt).visibility = View.VISIBLE
                    helper.setText(R.id.tvTime, item!!.create_time)
                            .setText(R.id.tvApplyNumber, "申请人:${item.topname}(守护者)")
                            .setText(R.id.tvText1, Html.fromHtml("<font color=#08C4AB>所在区域:</font>  <font color=#999999>没有数据</font>"))
                            .setText(R.id.tvText2, Html.fromHtml("<font color=#08C4AB>申请数量:</font>  <font color=#999999>${item.number}张</font>"))
                            .setText(R.id.tvText3, Html.fromHtml("<font color=#08C4AB>所在区域:</font>  <font color=#999999>没有数据</font>"))

                    when (item.apply_status) {
                        0 -> { //  审核中
                            helper.setText(R.id.tvTime, item!!.create_time)
                                    .setText(R.id.tvApplyNumber, "申请人:${item.topname}(守护者)")
                                    .setText(R.id.tvText1, Html.fromHtml("<font color=#08C4AB>所在区域:</font>  <font color=#999999>没有数据</font>"))
                                    .setText(R.id.tvText2, Html.fromHtml("<font color=#08C4AB>申请数量:</font>  <font color=#999999>${item.number}张</font>"))
                                    .setText(R.id.tvState, Html.fromHtml("<font color=#FFA200>等您确认分配...</font>"))
                            helper.getView<TextView>(R.id.tvText3).visibility = View.GONE
                            helper.getView<TextView>(R.id.tvCommit).visibility = View.VISIBLE
                            helper.getView<ConstraintLayout>(R.id.clView).setBackgroundResource(R.drawable.sp_green_10_b0)
                            helper.getView<TextView>(R.id.tvCommit).onClick {
                                mPresenter.agrApply(AgrApplyReq(list[helper.layoutPosition].apply_id))
                            }
                        }
                        1 -> { // 通过
                            helper.setText(R.id.tvTime, item!!.create_time)
                                    .setText(R.id.tvApplyNumber, "申请人:${item.topname}(守护者)")
                                    .setText(R.id.tvText1, Html.fromHtml("<font color=#08C4AB>所在区域:</font>  <font color=#999999>没有数据</font>"))
                                    .setText(R.id.tvText2, Html.fromHtml("<font color=#08C4AB>号段:</font>  <font color=#999999>${item.number}张</font>"))
                                    .setText(R.id.tvText3, Html.fromHtml("<font color=#08C4AB>分配数量:</font>  <font color=#999999>没有数据</font>"))
                                    .setText(R.id.tvState, Html.fromHtml("<font color=#999999>已完成分配，如有疑问请联系客服</font>"))
                            helper.getView<TextView>(R.id.tvText3).visibility = View.VISIBLE
                            helper.getView<TextView>(R.id.tvCommit).visibility = View.GONE
                            helper.getView<ConstraintLayout>(R.id.clView).setBackgroundResource(R.drawable.sp_green_10_b0)
                        }
                    }
                }
            }
        }

        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem14_14_14(act))
        if (type == 1){
            adapter.emptyView = getEmptyView(act, R.mipmap.empty_colloect," 暂无卡号申请记录，赶快申请吧~")
        }
        if (type == 2){
            adapter.emptyView = getEmptyView(act, R.mipmap.empty_colloect," 暂无卡号分配记录，赶快申请吧~")
        }
        adapter.setOnItemClickListener { _, _, position ->

        }

        tvApply.onClick {
            dialog.show()
        }

        srView.refresh({
            page == 1
            list.clear()
            initData()
        },{
            page++
            initData()
        })
    }

    private fun initData() {
        if (type == 1) {
            llText.visibility = View.VISIBLE
            llBt.visibility = View.VISIBLE
            mPresenter.phoneNumberList(PhoneNumberReq(0,page))

        }
        if (type == 2) {
            llText.visibility = View.GONE
            llBt.visibility = View.GONE
            mPresenter.myDistribution(PhoneNumberReq(0,page))
        }
    }

    private fun addVipCard() {
        if (dialog.getView<EditText>(R.id.edRemark).text.isNotEmpty() &&
                dialog.getView<EditText>(R.id.edNumber).text.isNotEmpty()) {
            var desc = dialog.getView<EditText>(R.id.edRemark).text.toString()
            var num = dialog.getView<EditText>(R.id.edNumber).text.toString().toInt()
            mPresenter.addVipCard(AddVipCardReq(desc, num))
        } else {
            toast("信息不完整")
        }
    }
}