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
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.support.v4.act

@SuppressLint("ValidFragment")
class RebateRecordFragment(var type: Int) : Fragment() {

    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String, BaseViewHolder>

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
        adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_rebate_profit, list) {
            override fun convert(helper: BaseViewHolder?, item: String?) {
                if (type == 1) {
                    helper!!.setText(R.id.tvText1, "健康电蒸锅")
                            .setText(R.id.tvText2, "花碗米")
                            .setText(R.id.tvState, "未提现")
                            .setText(R.id.tvNum, "¥ 3580.0")
                            .setText(R.id.tvNoNum, "21%")
                            .setText(R.id.tvYJHNum, "¥ 751.8")
                            .setText(R.id.tvApplyNumber, "合伙人:李骁")
                    helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.orange))

                    helper.getView<RecyclerView>(R.id.rvView).run {
                        adapter = object :BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_rebate_profit_item, mutableListOf()){
                            override fun convert(helper: BaseViewHolder?, item: String?) {

                            }
                        }
                        layoutManager = GridLayoutManager(act, 1, RecyclerView.HORIZONTAL, false)
                    }
                } else {
                    helper!!.setText(R.id.tvText1, "空气净化器")
                            .setText(R.id.tvText2, "暖宫宝")
                            .setText(R.id.tvState, "未提现")
                            .setText(R.id.tvState, "未提现")
                            .setText(R.id.tvNum, "¥ 2780.0")
                            .setText(R.id.tvNoNum, "18%")
                            .setText(R.id.tvYJHNum, "¥ 500.4")
                            .setText(R.id.tvApplyNumber, "合伙人:王丽")
                    helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act, R.color.orange))
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem14_14_14(act))
        adapter.emptyView = getEmptyView(act,R.mipmap.empty_colloect,"暂无商品返利~")
    }

    private fun initData() {}
}