package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.Logistics
import com.wuhanzihai.rbk.ruibeikang.data.entity.LogisticsData
import com.wuhanzihai.rbk.ruibeikang.data.protocal.LogisticsReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.LogisticsPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.LogisticsView
import kotlinx.android.synthetic.main.activity_logistics.*
import org.jetbrains.anko.act

class LogisticsActivity : BaseMvpActivity<LogisticsPresenter>(),LogisticsView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onLogisticsResult(result: Logistics) {
        list.addAll(result.data)
        adapter.notifyDataSetChanged()
    }

    private lateinit var list: MutableList<LogisticsData>
    private lateinit var adapter: BaseQuickAdapter<LogisticsData,BaseViewHolder>

    private var orderId = 0
    private var storeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logistics)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        orderId = intent.getIntExtra("orderId",0)
        storeId = intent.getIntExtra("storeId",0)

        initView()

        initData()
    }

    private fun initView(){
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<LogisticsData,BaseViewHolder>(R.layout.item_logistics,list){
            override fun convert(helper: BaseViewHolder?, item: LogisticsData?) {
                if (helper!!.layoutPosition == 0){
                    helper.getView<View>(R.id.vLine1).visibility = View.GONE
                }
                helper.setText(R.id.tvText,item!!.context)
                        .setText(R.id.tvTime,item.time)

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,1)

    }

    private fun initData(){
        mPresenter.logistics(LogisticsReq(storeId,orderId))

    }
}
