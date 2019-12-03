package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.ApplyCashListBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.ApplyCashListItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ApplyCashListReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamIdPageReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemLine
import com.wuhanzihai.rbk.ruibeikang.presenter.ApplyCashPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ApplyCashView
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.support.v4.act

@SuppressLint("ValidFragment")
class ApplyCashRecordFragment(var type: Int) : BaseMvpFragment<ApplyCashPresenter>(), ApplyCashView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onApplyCashListResult(result: ApplyCashListBean) {
        srView.finish()
        list.addAll(result.list)
        adapter.notifyDataSetChanged()
    }

    private lateinit var list: MutableList<ApplyCashListItem>
    private lateinit var adapter: BaseQuickAdapter<ApplyCashListItem, BaseViewHolder>
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
        adapter = object : BaseQuickAdapter<ApplyCashListItem, BaseViewHolder>(R.layout.item_apply_cash, list) {
            override fun convert(helper: BaseViewHolder?, item: ApplyCashListItem?) {
                helper!!.setText(R.id.tvState, item!!.desc)
                        .setText(R.id.tvDesc, item.error_desc)
                        .setText(R.id.tvTime, item.create_time)
                        .setText(R.id.tvMoney, "¥ ${item.money}")
                if (item.status == "3") {
                    helper.getView<TextView>(R.id.tvDesc).visibility = View.VISIBLE
                } else {
                    helper.getView<TextView>(R.id.tvDesc).visibility = View.GONE
                }
                when(type){
                    1->{
                        helper.getView<TextView>(R.id.tvMoney).setTextColor(ContextCompat.getColor(act,R.color.orange))
                    }
                    2->{
                        helper.getView<TextView>(R.id.tvMoney).setTextColor(ContextCompat.getColor(act,R.color.orange))
                    }
                    3->{
                        helper.getView<TextView>(R.id.tvMoney).setTextColor(ContextCompat.getColor(act,R.color.green_08))
                    }
                    4->{
                        helper.getView<TextView>(R.id.tvMoney).setTextColor(ContextCompat.getColor(act,R.color.red))
                    }
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemLine(act))
        adapter.emptyView = getEmptyView(act, R.mipmap.empty_colloect, " 暂无提现明细，申请提现后才能看到哦~")

        srView.refresh({
            page = 1
            list.clear()
            initData()
        }, {
            page++
            initData()
        })

    }

    private fun initData() {
        mPresenter.applyCashList(ApplyCashListReq(type, page))
    }
}