package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.UnifiedWebActivity
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoaderArt
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthBannerReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthListReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem12_14_12
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthInfoPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthInfoView
import kotlinx.android.synthetic.main.fragment_health_info.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

class HealthInfoFragment:BaseMvpFragment<HealthInfoPresenter>(),HealthInfoView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onHealthBannerResult(result: HealthBannerBean) {
        if (result.totle>0){
            mBanner.visibility = View.VISIBLE
        }
        mBanner.update(result.item)
    }

    override fun onHealthListResult(result: HealthListBean) {
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    private lateinit var adapter: BaseQuickAdapter<HealthListItem, BaseViewHolder>
    private lateinit var list: MutableList<HealthListItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_health_info,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        initView()

        initData()
    }

    private fun initView(){
        mBanner.setImageLoader(FrescoBannerLoaderArt())
                .start()

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
        adapter.setOnItemClickListener { _, _, position ->
            startActivity<UnifiedWebActivity>("id" to list[position].article_id)
        }
    }

    private fun initData(){
        mPresenter.healthInfoBanner(HealthBannerReq(arguments!!.getInt("cate_id")))
        mPresenter.healthInfoList(HealthListReq(arguments!!.getInt("cate_id"),1))
    }
}