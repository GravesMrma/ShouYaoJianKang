package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wuhanzihai.rbk.ruibeikang.R

import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.*
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamIdDisIdPageReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamPageReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import com.wuhanzihai.rbk.ruibeikang.presenter.ApplyCardPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.ApplyLevelPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ApplyCardView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ApplyLevelView
import kotlinx.android.synthetic.main.activity_apply_level_record.*
import org.jetbrains.anko.act

class CardStockRecordActivity : BaseMvpActivity<ApplyCardPresenter>(), ApplyCardView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onApplyCardRecord(result: CardRecord) {
        srView.finish()
        list.addAll(result.item)
        adapter.notifyDataSetChanged()
    }


    private lateinit var list: MutableList<CardRecordItem>
    private lateinit var adapter: BaseQuickAdapter<CardRecordItem, BaseViewHolder>

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_stock_record)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<CardRecordItem, BaseViewHolder>(R.layout.item_apply_card_record, list) {
            override fun convert(helper: BaseViewHolder?, item: CardRecordItem?) {
                helper!!.setText(R.id.tvTime,item!!.create_time)
                        .setText(R.id.tvCard,item.number.toString())
                        .setText(R.id.tvState,item.g_name)
                when(item.type_status){  // 1：待审核 2：审核通过 3：审核失败
                    "1"->{
                        helper.setText(R.id.tvName,"平台审核中...")
                        helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act,R.color.orange))
                        helper.getView<TextView>(R.id.tvText1).setTextColor(ContextCompat.getColor(act,R.color.orange))
                        helper.getView<TextView>(R.id.tvCard).setTextColor(ContextCompat.getColor(act,R.color.orange))
                    }
                    "2"->{
                        helper.setText(R.id.tvName,"审核通过")
                        helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act,R.color.green_08))
                        helper.getView<TextView>(R.id.tvText1).setTextColor(ContextCompat.getColor(act,R.color.green_08))
                        helper.getView<TextView>(R.id.tvCard).setTextColor(ContextCompat.getColor(act,R.color.green_08))
                    }
                    "3"->{
                        helper.setText(R.id.tvName,"审核失败")
                        helper.getView<TextView>(R.id.tvState).setTextColor(ContextCompat.getColor(act,R.color.red))
                        helper.getView<TextView>(R.id.tvText1).setTextColor(ContextCompat.getColor(act,R.color.red))
                        helper.getView<TextView>(R.id.tvCard).setTextColor(ContextCompat.getColor(act,R.color.red))
                    }
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem14_14_14(act))
        adapter.emptyView = getEmptyView(act, R.mipmap.empty_colloect, "暂无数据")

        srView.refresh({
            page = 1
            list.clear()
            initData()
        }, {
            page++
            initData()
        })
    }

    private fun initData() {
        mPresenter.applyCardRecord(NoParamIdDisIdPageReq(page))
    }
}
