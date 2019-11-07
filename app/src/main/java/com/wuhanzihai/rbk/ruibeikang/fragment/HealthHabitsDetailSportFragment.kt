package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.HealthHabitsDetailActivity
import com.wuhanzihai.rbk.ruibeikang.activity.UnifiedWebActivity
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthBannerReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthListReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem11_11_10
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemNews
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthHabitsPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthHabitsView
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

class HealthHabitsDetailSportFragment:BaseMvpFragment<HealthHabitsPresenter>(),HealthHabitsView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onHealthListResult(result: HealthListBean) {
        srView.finishRefresh()
        srView.finishLoadMore()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    private lateinit var adapter: BaseQuickAdapter<HealthListItem, BaseViewHolder>
    private lateinit var list: MutableList<HealthListItem>
    private var page = 1

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
        adapter = object : BaseQuickAdapter<HealthListItem,BaseViewHolder>(R.layout.item_health_info,list){
            override fun convert(helper: BaseViewHolder?, item: HealthListItem?) {
                helper!!.setText(R.id.tvDesc,item!!.title)
                        .setText(R.id.tvReadNum,item.click.toString())
                        .setText(R.id.tvJudgeNum,item.follow.toString())
                helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.thumb)
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        rvView.addItemDecoration(DividerItemNews(act))
        adapter.emptyView = getEmptyView(act,"暂无数据")
        adapter.setOnItemClickListener { _, _, position ->
            startActivity<UnifiedWebActivity>("id" to list[position].article_id)
        }

        srView.setOnRefreshListener {
            list.clear()
            page = 1
            initData()
        }

        srView.setOnLoadMoreListener {
            page++
            initData()
        }
    }

    private fun initData(){
        mPresenter.healthInfoList(HealthListReq(arguments!!.getInt("id"),page))
    }
}