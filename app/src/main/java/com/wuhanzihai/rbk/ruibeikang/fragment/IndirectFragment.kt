package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.DirectBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.DirectItem
import com.wuhanzihai.rbk.ruibeikang.data.entity.MyTeamBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DirectReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import com.wuhanzihai.rbk.ruibeikang.presenter.DirectPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.DirectView
import kotlinx.android.synthetic.main.fragment_direct.*
import org.jetbrains.anko.support.v4.act

class IndirectFragment: BaseMvpFragment<DirectPresenter>(), DirectView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onDirectResult(result: DirectBean) {

    }

    override fun onInDirectResult(result: DirectBean) {
        srView.finish()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
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

    private fun initView(){
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<DirectItem, BaseViewHolder>(R.layout.item_indirect, list) {
            override fun convert(helper: BaseViewHolder?, item: DirectItem?) {
                helper!!.setText(R.id.tvTime, "关联时间  ${item!!.create_time.substring(0,10)}")
                        .setText(R.id.tvApplyNumber, "${item.g_name}: ${item.nickname}")
                        .setText(R.id.tvName, item.mobile)
                        .setText(R.id.tvNumber, item.card_no)
                        .setText(R.id.tvYJHNum,  item.stock.toString())
                        .setText(R.id.tvWJHNum,  item.userdirectcount.toString())
//                        .setText(R.id.tvState, "")
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        rvView.addItemDecoration(DividerItem14_14_14(act))
        adapter.emptyView =  getEmptyView(act, R.mipmap.empty_team,"暂无下级成员，只有分销商才能查看下级成员哦~")

        mRgRecord.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.mRbAll->{

                }
                R.id.mRbSeven->{

                }
                R.id.mRbThirty->{

                }
                R.id.mRbNinety->{

                }
            }
        }

        srView.refresh({
            page == 1
            list.clear()
            initData()
        },{
            page++
            initData()
        })
    }

    private fun initData(){
        mPresenter.inDirectData(DirectReq(page,0))
        mPresenter.myTeam()
    }
}