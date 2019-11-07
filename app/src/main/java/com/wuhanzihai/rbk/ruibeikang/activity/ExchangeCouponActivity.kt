package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import kotlinx.android.synthetic.main.activity_exchange_coupon.*
import org.jetbrains.anko.act

class ExchangeCouponActivity : AppCompatActivity() {

    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_coupon)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView(){

        list = mutableListOf()
        adapter = object :BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_coupon,list){
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)
        adapter.emptyView = getEmptyView(act,R.mipmap.empty_order,"暂无可兑换优惠券")
    }

    private fun initData(){

    }
}
