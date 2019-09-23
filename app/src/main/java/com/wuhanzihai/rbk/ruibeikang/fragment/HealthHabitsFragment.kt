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
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem11_11_10
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthHabitsPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthHabitsView
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

class HealthHabitsFragment:BaseMvpFragment<HealthHabitsPresenter>(),HealthHabitsView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onHealthHabitsResult(result: MutableList<HealthHabitsBean>) {

    }

    private lateinit var list: MutableList<HealthHabitsListItem>
    private lateinit var adapter: BaseQuickAdapter<HealthHabitsListItem,BaseViewHolder>

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
        adapter = object : BaseQuickAdapter<HealthHabitsListItem,BaseViewHolder>(R.layout.item_health_habits,list){
            override fun convert(helper: BaseViewHolder?, item: HealthHabitsListItem?) {
                helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item!!.thumb)
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        rvView.addItemDecoration(DividerItem11_11_10(act))

        adapter.setOnItemClickListener { _, _, position ->
            startActivity<HealthHabitsDetailActivity>("id" to list[position].cat_id)
        }
    }

    private fun initData(){
        var data = arguments!!.getSerializable("data")
        list.addAll((data as HealthHabitsBundle).list)
        Log.e("数据是",list.size.toString()+"")
        adapter.notifyDataSetChanged()
    }
}