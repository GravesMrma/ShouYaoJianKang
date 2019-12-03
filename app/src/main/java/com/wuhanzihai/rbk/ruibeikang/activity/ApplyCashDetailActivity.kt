package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.ApplyCashRecordBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.ApplyCashRecordItem
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamIdPageReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import com.wuhanzihai.rbk.ruibeikang.presenter.ApplyCashPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ApplyCashView
import kotlinx.android.synthetic.main.activity_apply_cash_detail.*
import org.jetbrains.anko.act

class ApplyCashDetailActivity : BaseMvpActivity<ApplyCashPresenter>(), ApplyCashView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onRebateResult(result: RebateBean) {
        tvMoney.text = result.totlemoney
    }

    override fun onApplyCashRecordResult(result: ApplyCashRecordBean) {
        srView.finish()
        tvMoney.text = result.all_money
        list.addAll(result.list)
        adapter.notifyDataSetChanged()
    }

    private lateinit var list: MutableList<ApplyCashRecordItem>
    private lateinit var adapter: BaseQuickAdapter<ApplyCashRecordItem, BaseViewHolder>
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_cash_detail)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()

    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<ApplyCashRecordItem, BaseViewHolder>(R.layout.item_apply_cash_detail, list) {
            override fun convert(helper: BaseViewHolder?, item: ApplyCashRecordItem?) {
                helper!!.setText(R.id.tvName, item!!.types_name)
                        .setText(R.id.tvTime, "${item.create_time} 入账")
                        .setText(R.id.tvMoney, "¥ ${item.price}")

                if (item.types == 1) {  //fanyong
                    helper.setImageResource(R.id.ivImg, R.mipmap.ic_cash2)
                } else {
                    helper.setImageResource(R.id.ivImg, R.mipmap.ic_cash1)
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem14_14_14(act))
        adapter.emptyView = getEmptyView(act,R.mipmap.empty_colloect,"暂无数据")

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
        mPresenter.applyCashRecord(NoParamIdPageReq(page))
    }
}
