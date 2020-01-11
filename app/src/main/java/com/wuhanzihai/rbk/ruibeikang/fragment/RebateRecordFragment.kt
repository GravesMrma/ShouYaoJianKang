package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateRecordBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateRecordItem
import com.wuhanzihai.rbk.ruibeikang.data.entity.RecordProduct
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamDisIdPageReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamIdDisIdPageReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemRebateRecord
import com.wuhanzihai.rbk.ruibeikang.presenter.RebateRecordPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.RebateRecordView
import kotlinx.android.synthetic.main.item_rebate_record_head.view.*
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.support.v4.act

@SuppressLint("ValidFragment")
class RebateRecordFragment(var type: Int) : BaseMvpFragment<RebateRecordPresenter>(), RebateRecordView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onRebateRecord(result: RebateRecordBean) {
        srView.finish()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    private lateinit var list: MutableList<RebateRecordItem>
    private lateinit var adapter: BaseQuickAdapter<RebateRecordItem, BaseViewHolder>
    private var page = 1

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
        adapter = object : BaseQuickAdapter<RebateRecordItem, BaseViewHolder>(R.layout.item_rebate_profit, list) {
            override fun convert(helper: BaseViewHolder?, item: RebateRecordItem?) {
                helper!!.setText(R.id.tvTime, "${item!!.paid_time}")
                        .setText(R.id.tvSn, item!!.order_no)
                        .setText(R.id.tvMoney, "返利金额: ¥ ${item.currentagent_money}")
                        .setText(R.id.tvRule,"${item.currentagent_prop}%")
                helper.getView<RecyclerView>(R.id.rvView).run {
                    adapter = object :BaseQuickAdapter<RecordProduct,BaseViewHolder>(R.layout.item_rebate_profit_item, item.product){
                        override fun convert(helper: BaseViewHolder?, item: RecordProduct?) {
                            helper!!.setText(R.id.tvText,item!!.goodsname)
                                    .setText(R.id.tvNumber,"x${item.pro_num}")
                        }
                    }
                    layoutManager = GridLayoutManager(act, 1)
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemRebateRecord(act))
        adapter.emptyView = getEmptyView(act,R.mipmap.empty_colloect,"暂无商品返利~")
        var headView = layoutInflater.inflate(R.layout.item_rebate_record_head,null)
        val data = Hawk.get<RebateBean>(BaseConstant.REBATE_INFO)
        headView.tvName.text = data.g_name
        headView.rvRule1.text = "${data.default_direct_product}%"
        headView.rvRule2.text = "${data.default_indirect_product}%"
        adapter.addHeaderView(headView)
        srView.refresh({
            page = 1
            list.clear()
            initData()
        },{
            page++
            initData()
        })
    }

    private fun initData() {
        mPresenter.rebateRecord(NoParamDisIdPageReq(page))
    }
}