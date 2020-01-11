package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.DirectDetailActivity
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.DirectBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.DirectItem
import com.wuhanzihai.rbk.ruibeikang.data.entity.MyTeamBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DirectReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.*
import com.wuhanzihai.rbk.ruibeikang.presenter.DirectPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.DirectView
import kotlinx.android.synthetic.main.fragment_direct.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

// 直推下级
class DirectFragment : BaseMvpFragment<DirectPresenter>(), DirectView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onDirectResult(result: DirectBean) {
        srView.finish()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }

    override fun onInDirectResult(result: DirectBean) {

    }

    override fun onMyTeamResult(result: MyTeamBean) {
        tvText.text = (result.directcount + result.indirectcount).toString()
        tvText1.text = "直推下级:${result.directcount}人"
        tvText2.text = "间推下级:${result.indirectcount}人"
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

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<DirectItem, BaseViewHolder>(R.layout.item_direct, list) {
            override fun convert(helper: BaseViewHolder?, item: DirectItem?) {
                helper!!.setText(R.id.tvTime, "关联时间  ${item!!.create_time.substring(0, 10)}")
                        .setText(R.id.tvApplyNumber, "${item.g_name}")
                        .setText(R.id.tvName, "${item.mobile.substring(0,4)}****${item.mobile.substring(8)}")
                        .setText(R.id.tvNumber, item.card_no)
                        .setText(R.id.tvYJHNum, item.stock.toString())
                        .setText(R.id.tvWJHNum, item.userdirectcount.toString())
//                        .setText(R.id.tvState, "")
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem14_14_14(act))
        adapter.emptyView = getEmptyView(act, R.mipmap.empty_team, "暂无下级成员，只有分销商才能查看下级成员哦~")

        srView.refresh({
            page == 1
            list.clear()
            initData()
        }, {
            page++
            initData()
        })
    }

    private fun initData() {
        mPresenter.directData(DirectReq(page, 0))
        mPresenter.myTeam()
    }
}