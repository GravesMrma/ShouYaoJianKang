package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.DirectBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.DirectItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DirectReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import com.wuhanzihai.rbk.ruibeikang.presenter.DirectPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.DirectView
import kotlinx.android.synthetic.main.fragment_direct.*
import org.jetbrains.anko.support.v4.act

class IndirectFragment: BaseMvpFragment<DirectPresenter>(), DirectView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onDirectResult(result: DirectBean) {

    }

    override fun onInDirectResult(result: DirectBean) {

        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    private lateinit var list: MutableList<DirectItem>
    private lateinit var adapter: BaseQuickAdapter<DirectItem, BaseViewHolder>
    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_direct, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()

    }

    private fun initView(){
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<DirectItem, BaseViewHolder>(R.layout.item_direct,list){
            override fun convert(helper: BaseViewHolder?, item: DirectItem?) {
                helper!!.setText(R.id.tvTime,"关联时间  ${item!!.relation_time}")
                        .setText(R.id.tvApplyNumber,"${item.dg_name}: ${item.name}")
                        .setText(R.id.tvName,item.phone)
                        .setText(R.id.tvAddress,"${item.province}${item.city}${item.area}")
                        .setText(R.id.tvNumber,"未知数据")
                        .setText(R.id.tvYJHNum,"")
                        .setText(R.id.tvWJHNum,"")
                        .setText(R.id.tvState,"")

                helper.getView<RelativeLayout>(R.id.rlViewBt).visibility = View.GONE
                helper.getView<LinearLayout>(R.id.llNumber1).visibility = View.GONE

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        rvView.addItemDecoration(DividerItem14_14_14(act))
    }

    private fun initData(){
        mPresenter.inDirectData(DirectReq(page,0))
    }
}