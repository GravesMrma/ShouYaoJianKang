package com.wuhanzihai.rbk.ruibeikang.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.UnifiedWebActivity
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoaderArt
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthBannerReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.HealthListReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerInfoComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.InfoModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemNews
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthInfoPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthInfoView
import com.wuhanzihai.rbk.ruibeikang.widgets.ViewPagerIndicator
import kotlinx.android.synthetic.main.fragment_health_info.*
import kotlinx.android.synthetic.main.page_art.view.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.startActivityForResult

class HealthInfoFragment : BaseMvpFragment<HealthInfoPresenter>(), HealthInfoView {
    override fun injectComponent() {
        DaggerInfoComponent.builder().activityComponent(mActivityComponent)
                .infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onHealthBannerResult(result: HealthBannerBean) {
        if (result.totle > 0) {
            mBanner.visibility = View.VISIBLE
            llIndicator.visibility = View.VISIBLE
            tvTop.visibility = View.VISIBLE
        }


        if (viewsTop.isEmpty()){
            for (item in result.item) {
                var view = layoutInflater.inflate(R.layout.page_art, null)
                view.ivImg.loadImage(item.thumb)
                view.tvText.text = item.title
                view.onClick {
                    startActivity<UnifiedWebActivity>("id" to item.article_id)
                }
                viewsTop.add(view)
            }

            mBanner.addOnPageChangeListener(ViewPagerIndicator(act, llIndicator, viewsTop.size))
            viewPageAdapter.notifyDataSetChanged()
        }
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

    private var viewsTop = mutableListOf<View>()
    private lateinit var viewPageAdapter: ViewPageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_health_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        mPresenter.healthInfoBanner(HealthBannerReq(arguments!!.getInt("cate_id")))
        page = 1
        initData()
    }

    private fun initView() {
        viewPageAdapter = ViewPageAdapter(viewsTop)
        mBanner.adapter = viewPageAdapter

        list = mutableListOf()
        adapter = object : BaseQuickAdapter<HealthListItem, BaseViewHolder>(R.layout.item_health_info, list) {
            override fun convert(helper: BaseViewHolder?, item: HealthListItem?) {
                helper!!.setText(R.id.tvDesc, item!!.title)
                        .setText(R.id.tvReadNum, item.click.toString())
                        .setText(R.id.tvJudgeNum, item.follow.toString())
                helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.thumb)
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemNews(act))
        adapter.emptyView = getEmptyView(act, "暂无数据,请下拉刷新")

        adapter.setOnItemClickListener { _, _, position ->
            this.position = position
            startActivityForResult<UnifiedWebActivity>(1234, "id" to list[position].article_id)
        }

        srView.setOnRefreshListener {
            page = 1
            list.clear()
            mPresenter.healthInfoBanner(HealthBannerReq(arguments!!.getInt("cate_id")))
            initData()
        }
        srView.setOnLoadMoreListener {
            page++
            initData()
        }
    }

    private fun initData() {
        mPresenter.healthInfoList(HealthListReq(arguments!!.getInt("cate_id"), page))
    }

    private var position = 0
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == 4321) {
            adapter.data[position].follow = data!!.getIntExtra("like", 0)
            adapter.notifyItemChanged(position)
        }
    }

    class ViewPageAdapter(var list: MutableList<View>) : PagerAdapter() {
        override fun getCount(): Int {
            return list.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(list[position])
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(list[position])
            return list[position]
        }
    }
}