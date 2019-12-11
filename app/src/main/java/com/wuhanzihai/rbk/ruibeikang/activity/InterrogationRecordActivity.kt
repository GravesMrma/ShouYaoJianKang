package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.common.showChoseText
import com.wuhanzihai.rbk.ruibeikang.data.entity.InterrogationBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.InterrogationItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamOrderIdReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemFifteen
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemTen
import com.wuhanzihai.rbk.ruibeikang.presenter.InterrogationPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.InterrogationView
import com.wuhanzihai.rbk.ruibeikang.widgets.CircleImageView
import kotlinx.android.synthetic.main.activity_interrogation_record.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*

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
                helper!!
                        .setText(R.id.tvTime, item!!.create_time)
                        .setText(R.id.tvContent, item.content)

                when (item.status) {
                    1 -> {
                        helper.setText(R.id.tvState, "未支付")
                        helper.setText(R.id.tvName, "未支付")
                        helper.getView<CircleImageView>(R.id.ivHead).setImageResource(R.mipmap.ic_launcher)
                    }
                    2 -> {
                        if (item.reading == 1) {
                            helper.setText(R.id.tvState, "医生未回复")
                        } else {
                            helper.setText(R.id.tvState, "医生已回复")
                        }
                        if (item.doctor_content.msg.contains("医生分配中")) {
                            helper.setText(R.id.tvName, "医生分配中")
                            helper.getView<CircleImageView>(R.id.ivHead).setImageResource(R.mipmap.ic_launcher)
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
                        helper.setText(R.id.tvName, "超时关闭")
                        helper.getView<CircleImageView>(R.id.ivHead).setImageResource(R.mipmap.ic_launcher)
                    }
                    4 -> {
                        helper.setText(R.id.tvState, "正常完成")
                        if (item.doctor_content.error == 0) {
                            helper.setText(R.id.tvName, item.doctor_content.name)
                                    .setText(R.id.tvTime, "${item.create_time}  ${item.doctor_content.clinic_name}")
                            helper.getView<CircleImageView>(R.id.ivHead).loadImage(item.doctor_content.image)
                        }
                    }
                    5 -> {
                        helper.setText(R.id.tvState, "取消订单")
                        helper.setText(R.id.tvName, "取消订单")
                        helper.getView<CircleImageView>(R.id.ivHead).setImageResource(R.mipmap.ic_launcher)
                    }
                    6 -> {
                        helper.setText(R.id.tvState, "退款")
                        helper.setText(R.id.tvName, "退款")
                        helper.getView<CircleImageView>(R.id.ivHead).setImageResource(R.mipmap.ic_launcher)
                    }
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemTen(act))
        adapter.setOnItemClickListener { _, _, position ->
            if (list[position].status == 2) {
                if (!list[position].doctor_content.msg.contains("医生分配中")) {
                    startActivity<ChatRoomActivity>("orderId" to list[position].order_id, "stateId" to list[position].status)
                }
            }
            if (list[position].status == 4) {
                startActivity<ChatRoomActivity>("orderId" to list[position].order_id, "stateId" to list[position].status)
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
            initData()
        }, {
            initData()
        })
    }

    private fun initData() {
        mPresenter.doctorRecord()
    }
}
