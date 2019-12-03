package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import com.wuhanzihai.rbk.ruibeikang.activity.ChatRoomActivity
import com.wuhanzihai.rbk.ruibeikang.common.showChoseText
import com.wuhanzihai.rbk.ruibeikang.data.entity.InterrogationBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.InterrogationItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DelArchivesReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamOrderIdReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.InterrogationPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.InterrogationView
import java.text.SimpleDateFormat
import java.util.*

class InterrogationRecordFragment : BaseMvpFragment<InterrogationPresenter>(), InterrogationView {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<InterrogationItem, BaseViewHolder>(R.layout.item_interrogation_record, list) {
            override fun convert(helper: BaseViewHolder?, item: InterrogationItem?) {
                helper!!.setText(R.id.tvName, "没数据")
                        .setText(R.id.tvTime, SimpleDateFormat("yyyy/MM/dd HH:mm").format(Date(item!!.create_time * 1000)))
                        .setText(R.id.tvContent, "没数据")
                        .setText(R.id.tvName, "没数据")
                        .setText(R.id.tvName, "没数据")

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        adapter.setOnItemClickListener { _, _, position ->
            startActivity<ChatRoomActivity>("orderId" to list[position].order_id,"stateId" to list[position].status)
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