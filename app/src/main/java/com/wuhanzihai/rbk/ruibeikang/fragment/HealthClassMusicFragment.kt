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
import com.eightbitlab.rxbus.Bus
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthClassDetailMusicReq
import com.wuhanzihai.rbk.ruibeikang.event.MusicEvent
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.media.MusicService
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthClassPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthClassView
import kotlinx.android.synthetic.main.layout_recyclerview.rvView
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startService

@SuppressLint("ValidFragment")
class HealthClassMusicFragment(var mc_id: Int) : BaseMvpFragment<HealthClassPresenter>(), HealthClassView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onHealthClassResult(result: HealthClassBean) {

    }

    override fun onHealthBannerResult(result: HealthClassBannerBean) {
    }

    override fun onHealthClassDetailResult(result: HealthClassDetailBean) {
    }

    override fun onHealthClassDetailMusicResult(result: HealthClassDetailMusicBean) {
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    private lateinit var list: MutableList<MusicItem>
    private lateinit var adapter: BaseQuickAdapter<MusicItem, BaseViewHolder>
    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_recyclerview, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<MusicItem, BaseViewHolder>(R.layout.item_health_class_item, list) {
            override fun convert(helper: BaseViewHolder?, item: MusicItem?) {
                helper!!.setText(R.id.tvName, item!!.music_title)
                        .setText(R.id.tvLisNum, "预计时长")
                        .setText(R.id.tvTime, item.music_date)
                helper.getView<TextView>(R.id.tvName).isSelected = item.isPlaying
                helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.music_img)
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        adapter.setOnItemClickListener { _, _, position ->
            if (!list[position].isPlaying) {
                for (item in list) {
                    item.isPlaying = false
                }
                list[position].isPlaying = !list[position].isPlaying
                adapter.notifyDataSetChanged()
                startService<MusicService>("data" to MusicEvent(list, position))
            }
        }
    }

    private fun initData() {
        mPresenter.healthClassDetailMusic(HealthClassDetailMusicReq(mc_id, page))
    }

    fun setSelect(music_id: Int) {
        for (item in list) {
            item.isPlaying = item.music_id == music_id
        }
        adapter.notifyDataSetChanged()
    }
}