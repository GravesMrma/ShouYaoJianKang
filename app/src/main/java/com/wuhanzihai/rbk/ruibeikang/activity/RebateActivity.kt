package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.hhjt.baselibrary.utils.LoginUtils
import com.jaeger.library.StatusBarUtil
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.CashDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.MineBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.RebateBean
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.RebatePresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.RebateView
import kotlinx.android.synthetic.main.activity_rebate.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

// 返利
class RebateActivity : BaseMvpActivity<RebatePresenter>(), RebateView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onRebateResult(result: RebateBean) {
        Hawk.put(BaseConstant.REBATE_INFO, result)
        LoginUtils.saveRebateId(result.agent_id)
        tvName.text = result.g_name
        tvName1.text = "用户：${result.g_name}"
        tvMoney1.text = "¥" + result.money

        if (result.dg_id == 0) {
            clTwo.visibility = View.VISIBLE
        }else{
            clOne.visibility = View.VISIBLE
        }
        srView.finish()
    }

    override fun onUserInfoResult(result: LoginData) {
        srView.finish()
        ivHead.loadImage(result.head_pic)
        ivHead1.loadImage(result.head_pic)
    }

    override fun onCashDetailResult(result: CashDetailBean) {
        tvMoney2.text = "¥" + result.agent.aplliy_money
    }

    private lateinit var list: MutableList<RebateItem>
    private lateinit var adapter: BaseQuickAdapter<RebateItem, BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rebate)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<RebateItem, BaseViewHolder>(R.layout.item_rebate, list) {
            override fun convert(helper: BaseViewHolder?, item: RebateItem?) {
                helper!!.setText(R.id.tvText, item!!.name)
                        .setImageResource(R.id.ivImg, item.res)
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 3)
        adapter.setOnItemClickListener { _, _, position ->
            when (position) {
                0 -> startActivity<CardStockActivity>()  //  号段申请
                1 -> startActivity<MyTeamActivity>() //  我的团队
                2 -> startActivity<ShareHealthActivity>()  //分享赚钱
//                2 -> startActivity<DirectlySuperiorActivity>() // 我的上级
                3 -> startActivity<RebateRecordActivity>() //商品返利
                4 -> startActivity<ApplyCashRecordActivity>()  //提现明细
//                5 ->  startActivity<ApplyLevelActivity>() // 等级申请
                5 -> startActivity<ShareRecordActivity>()  //分享记录
//                8 -> startActivity<ApplyCardActivity>()  //制卡申请
            }
        }

        rlViewl.onClick {
            startActivity<ShareHealthActivity>()
        }
        rlView2.onClick {
            startActivity<ShareRecordActivity>()
        }

//        startActivity<StandardWebActivity>("title" to "返利规则"
//                , "data" to "http://api.hcjiankang.com/api/Web/article?id=773")
        tvTixian.onClick {
            startActivity<ApplyCashActivity>()
        }

        llApply.onClick {
            startActivity<ApplyCashDetailActivity>()
        }
        llAuth.onClick {
            startActivity<ApplyCashRecordActivity>()
        }
        srView.refresh({
            mPresenter.disbutorIndex()
            mPresenter.getUserInfo()
            mPresenter.disbutorIndex()
        }, {
            srView.setNoMoreData(true)
        })
    }

    private fun initData() {
        list.add(RebateItem(R.mipmap.ic_rebate1, "礼包库存"))
        list.add(RebateItem(R.mipmap.ic_rebate2, "我的客户"))
        list.add(RebateItem(R.mipmap.ic_rebate7, "分享赚钱"))
//        list.add(RebateItem(R.mipmap.ic_rebate3, "直属上级"))
        list.add(RebateItem(R.mipmap.ic_rebate4, "商品返利"))
        list.add(RebateItem(R.mipmap.ic_rebate5, "提现明细"))
//        list.add(RebateItem(R.mipmap.ic_rebate6, "等级申请"))
        list.add(RebateItem(R.mipmap.ic_rebate8, "分享记录"))
//        list.add(RebateItem(R.mipmap.ic_rebate9, "制卡申请"))
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.applyCashDetail()
        mPresenter.disbutorIndex()
        mPresenter.getUserInfo()
    }

    inner class RebateItem(var res: Int, var name: String)
}
