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
import com.eightbitlab.rxbus.Bus
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.data.entity.MusicBannerBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.MusicDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.MusicItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.MusicBannerReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.MusicReq
import com.wuhanzihai.rbk.ruibeikang.event.MainEvent
import com.wuhanzihai.rbk.ruibeikang.event.MusicEvent
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemMusic
import com.wuhanzihai.rbk.ruibeikang.media.MusicService
import com.wuhanzihai.rbk.ruibeikang.presenter.MusicPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MusicView
import kotlinx.android.synthetic.main.fragment_music.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startService

@SuppressLint("ValidFragment")
class MusicFragment(var carID: Int) : BaseMvpFragment<MusicPresenter>(), MusicView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onMusicDetailResult(result: MusicDetailBean) {
        srView.finishLoadMore()
        srView.finishRefresh()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    override fun onMusicBannerResult(result: MusicBannerBean) {
        if (result.item.isEmpty()) {
            clView.visibility = View.GONE
        } else {
            clView.visibility = View.VISIBLE
            mBanner.update(result.item)
        }
    }

    private lateinit var list: MutableList<MusicItem>
    private lateinit var adapter: BaseQuickAdapter<MusicItem, BaseViewHolder>
    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        mBanner.setImageLoader(FrescoBannerLoader(true))
                .start()

        list = mutableListOf()
        adapter = object : BaseQuickAdapter<MusicItem, BaseViewHolder>(R.layout.item_music, list) {
            override fun convert(helper: BaseViewHolder?, item: MusicItem?) {
                helper!!.setText(R.id.tvText2, item!!.music_desc)
                        .setText(R.id.tvText1, item.music_title)
                        .setText(R.id.tvText3, "${item.music_startnumber}次收听")

                if (helper.layoutPosition % 2 == 1) {
                    helper.setText(R.id.tvText3, "1000+次收听")
                } else {
                    helper.setText(R.id.tvText3, "2000+次收听")
                }
                helper.getView<SimpleDraweeView>(R.id.ivImage).loadImage(item.music_img)

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 2)
        rvView.addItemDecoration(DividerItemMusic(act))
        adapter.setOnItemClickListener { adapter, view, position ->
            startService<MusicService>("data" to MusicEvent(list, position))
            Bus.send(MainEvent())
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

    private fun initData() {
        mPresenter.musicList(MusicReq(carID, page))
        mPresenter.musicBanner(MusicBannerReq(carID))
    }
}