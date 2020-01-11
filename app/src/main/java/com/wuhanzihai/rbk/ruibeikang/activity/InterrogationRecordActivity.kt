package com.wuhanzihai.rbk.ruibeikang.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.common.showChoseText
import com.wuhanzihai.rbk.ruibeikang.data.entity.InterrogationBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.InterrogationItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamOrderIdReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemTen
import com.wuhanzihai.rbk.ruibeikang.presenter.InterrogationPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.InterrogationView
import com.wuhanzihai.rbk.ruibeikang.widgets.CircleImageView
import kotlinx.android.synthetic.main.activity_interrogation_record.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class InterrogationRecordActivity : BaseMvpActivity<InterrogationPresenter>(), InterrogationView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onDoctorRecordResult(result: InterrogationBean) {
        srView.finish()
        list.addAll(result.order)
        adapter.notifyDataSetChanged()
    }

    override fun onDelRecordResult() {
        list.clear()
        initData()
    }

    private lateinit var list: MutableList<InterrogationItem>
    private lateinit var adapter: BaseQuickAdapter<InterrogationItem, BaseViewHolder>
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interrogation_record)

        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<InterrogationItem, BaseViewHolder>(R.layout.item_interrogation_record, list) {
            override fun convert(helper: BaseViewHolder?, item: InterrogationItem?) {
                helper!!.setText(R.id.tvTime, item!!.create_time)
                        .setText(R.id.tvContent, item.content)
                when (item.status) {
                    1 -> {
                        helper.setText(R.id.tvState, "未支付")
                        helper.setText(R.id.tvName, "未支付")
                        helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.black_33))
                        helper.getView<CircleImageView>(R.id.ivHead).setImageResource(R.mipmap.ic_launcher)
                    }
                    2 -> {
                        if (item.reading == 1) {
                            helper.setText(R.id.tvState, "医生未回复")
                            helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.red))
                        } else {
                            helper.setText(R.id.tvState, "医生已回复")
                            helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.orange))
                        }
                        if (item.doctor_content.msg.contains("医生分配中")) {
                            helper.setText(R.id.tvName, "医生分配中")
                            helper.getView<CircleImageView>(R.id.ivHead).setImageResource(R.mipmap.ic_launcher)
                            helper.setText(R.id.tvState, "新问题")
                            helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.black_33))
                        } else {
                            if (item.doctor_content.error == 0) {
                                helper.setText(R.id.tvName, item.doctor_content.name)
                                        .setText(R.id.tvTime, "${item.create_time}  ${item.doctor_content.clinic_name}")
                                helper.getView<CircleImageView>(R.id.ivHead).loadImage(item.doctor_content.image)
                            }
                        }
                    }
                    3 -> {
                        helper.setText(R.id.tvState, "超时关闭")
                        helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.black_33))
                        helper.setText(R.id.tvName, "超时关闭")
                        helper.getView<CircleImageView>(R.id.ivHead).setImageResource(R.mipmap.ic_launcher)
                    }
                    4 -> {
                        helper.setText(R.id.tvState, "正常完成")
                        helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.black_33))
                        if (item.doctor_content.error == 0) {
                            helper.setText(R.id.tvName, item.doctor_content.name)
                                    .setText(R.id.tvTime, "${item.create_time}  ${item.doctor_content.clinic_name}")
                            helper.getView<CircleImageView>(R.id.ivHead).loadImage(item.doctor_content.image)
                        }
                    }
                    5 -> {
                        helper.setText(R.id.tvState, "取消订单")
                        helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.black_33))
                        helper.setText(R.id.tvName, "取消订单")
                        helper.getView<CircleImageView>(R.id.ivHead).setImageResource(R.mipmap.ic_launcher)
                    }
                    6 -> {
                        helper.setText(R.id.tvState, "退款")
                        helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.black_33))
                        helper.setText(R.id.tvName, "退款")
                        helper.getView<CircleImageView>(R.id.ivHead).setImageResource(R.mipmap.ic_launcher)
                    }
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemTen(act))
        adapter.emptyView = getEmptyView(act,R.mipmap.empty_colloect,"暂无问诊记录~")
        adapter.setOnItemClickListener { _, _, position ->
            if (list[position].status == 4 || list[position].status == 2|| list[position].status == 3) {
                startActivity<ChatRoomActivity>("orderId" to list[position].order_id)
            }
        }
        adapter.setOnItemLongClickListener { adapter, view, position ->
            showChoseText(act, "确认删除该条记录吗?", "删除") {
                mPresenter.deleteRecord(NoParamOrderIdReq(list[position].order_id))
            }
            return@setOnItemLongClickListener true
        }

        srView.refresh({
            list.clear()
            page = 1
            initData()
        }, {
            page++
            initData()
        })
    }

    private fun initData() {
        mPresenter.doctorRecord(page)
    }
}
