package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.wuhanzihai.rbk.ruibeikang.R
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.common.FrescoBannerLoader
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerIndexComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.IndexModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemNews
import com.wuhanzihai.rbk.ruibeikang.presenter.MainFragmentPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MainView
import com.wuhanzihai.rbk.ruibeikang.utils.TimeUtils
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

class MainFragment:BaseMvpFragment<MainFragmentPresenter>(),MainView {

    override fun injectComponent() {
        DaggerIndexComponent.builder().activityComponent(mActivityComponent)
                .indexModule(IndexModule()).build().inject(this)
        mPresenter.mView = this
    }

    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>

    private lateinit var newsList: MutableList<HealthListItem>
    private lateinit var newsAdapter: BaseQuickAdapter<HealthListItem,BaseViewHolder>

    private lateinit var indexClockBean :IndexClockBean

    override fun onIndexDate(result: IndexBean) {
        newsList.addAll(result.item)
        newsAdapter.notifyDataSetChanged()
    }

    override fun onIndexAdv(result: IndexAdvBean) {
        ivImgOne.loadImage(result.one.url)
        ivImgTwo.loadImage(result.two.url)
    }

    override fun onIndexBanner(result: IndexBannerBean) {
        mBanner.setImageLoader(FrescoBannerLoader(false))
                .setImages(result.item)
                .start()
    }

    override fun onIndexClock(result: IndexClockBean) {
        indexClockBean = result
        tvText.text = result.titlle
        tvContent.text = result.description
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()
    }

    private fun initView(){
        rlHeathInfo.onClick {
            startActivity<HealthInfoActivity>()
        }
        rlHeathHabits.onClick {
            startActivity<HealthHabitsActivity>()
        }
        rlHeathCheck.onClick {
            startActivity<HealthCheckActivity>()
        }
        rlHeathCare.onClick {
            startActivity<HealthCareActivity>()
        }
        clQuestion.onClick {
            startActivity<QuestionActivity>()
        }
        lClock.onClick {
            startActivity<ClockDetailActivity>("data" to indexClockBean.content)
        }

        list = mutableListOf()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_main_sport,list){
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }
        rvSport.adapter = adapter
        rvSport.layoutManager = GridLayoutManager(act,1,RecyclerView.HORIZONTAL,false)
        adapter.setOnItemClickListener { _, _, _ ->
            startActivity<BindBraceletActivity>()
        }

        newsList = mutableListOf()
        newsAdapter = object : BaseQuickAdapter<HealthListItem,BaseViewHolder>(R.layout.item_home_news,newsList){
            override fun convert(helper: BaseViewHolder?, item: HealthListItem?) {
                helper!!.setText(R.id.tvDesc,item!!.title)
                        .setText(R.id.tvReadNum,item.click.toString())
                        .setText(R.id.tvJudgeNum,item.follow.toString())
                helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.thumb)
            }
        }
        rvView.adapter = newsAdapter
        rvView.layoutManager = GridLayoutManager(act,1)
        rvView.addItemDecoration(DividerItemNews(act))
    }

    private fun initData(){
        tvText1.text = TimeUtils.getInstance().getHour()
        mPresenter.indexBanner()
        mPresenter.indexAdv()
        mPresenter.indexClock()
        mPresenter.indexInfo()
    }
}