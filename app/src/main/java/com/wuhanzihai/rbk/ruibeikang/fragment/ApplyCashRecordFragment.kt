package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.ApplyCashPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ApplyCashView
import kotlinx.android.synthetic.main.layout_recyclerview.*
import org.jetbrains.anko.support.v4.act

@SuppressLint("ValidFragment")
class ApplyCashRecordFragment(var type: Int): BaseMvpFragment<ApplyCashPresenter>(),ApplyCashView{
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

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

    private fun initView(){
        list = mutableListOf()
        if (type == 1){
            list.add("")
        }
        adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_apply_cash,list){
            override fun convert(helper: BaseViewHolder?, item: String?) {

                when(helper!!.layoutPosition){

                    0->{
                        helper.setText(R.id.tvState,"提现金额审核中...")
                                .setText(R.id.tvDesc,"提现规则调整中，请联系客服！")
                                .setText(R.id.tvTime,"2019-11-12  09:24")
                                .setText(R.id.tvMoney,"¥ 2500")
                        helper.getView<TextView>(R.id.tvDesc).visibility = View.GONE
                    }
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        adapter.emptyView = getEmptyView(act,"暂无数据")
    }

    private fun initData(){}
}