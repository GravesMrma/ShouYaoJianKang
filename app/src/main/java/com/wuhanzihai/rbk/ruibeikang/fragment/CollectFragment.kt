package com.wuhanzihai.rbk.ruibeikang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.SubjectDetailActivity
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.CollectBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.CollectListReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.presenter.CollectPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.CollectView
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

@SuppressLint("ValidFragment")
class CollectFragment:BaseMvpFragment<CollectPresenter>(),CollectView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onCollectResult(result: MutableList<CollectBean>) {
        list.addAll(result)
        adapter.notifyDataSetChanged()
    }

    private lateinit var list: MutableList<CollectBean>
    private lateinit var adapter: BaseQuickAdapter<CollectBean, BaseViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_recyclerview,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()

    }

    private fun initView(){
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<CollectBean, BaseViewHolder>(R.layout.item_collect,list){
            override fun convert(helper: BaseViewHolder?, item: CollectBean?) {

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        adapter.emptyView = getEmptyView(act,R.mipmap.empty_colloect,"暂无收藏")
        adapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    private fun initData(){
        mPresenter.collectList(CollectListReq("1",1))
    }
}