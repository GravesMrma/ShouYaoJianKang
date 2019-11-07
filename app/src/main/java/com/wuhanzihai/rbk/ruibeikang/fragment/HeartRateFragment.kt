package com.wuhanzihai.rbk.ruibeikang.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.mltcode.blecorelib.bean.Heartrate
import com.android.mltcode.blecorelib.bean.HeartrateBean
import com.android.mltcode.blecorelib.mode.CallbackMode
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.eightbitlab.rxbus.Bus
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.BraceletData
import com.wuhanzihai.rbk.ruibeikang.event.BraceletDataEvent
import kotlinx.android.synthetic.main.fragment_heart_rate.*
import org.jetbrains.anko.support.v4.act
import java.text.SimpleDateFormat
import java.util.*

class HeartRateFragment : Fragment() {

    private lateinit var adapter: BaseQuickAdapter<Heartrate, BaseViewHolder>
    private lateinit var list: MutableList<Heartrate>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_heart_rate, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()
    }

    fun initView() {
        val format = SimpleDateFormat("MM月dd日 EE")
        tvTime.text = format.format(Date(System.currentTimeMillis()))
        if (BraceletData.instance.batteryBean != null){
            tvBat.text = BraceletData.instance.batteryBean!!.percent.toString() + "%"
        }
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<Heartrate, BaseViewHolder>(R.layout.item_heart, list) {
            override fun convert(helper: BaseViewHolder?, item: Heartrate?) {
                helper!!.setText(R.id.tvData, item!!.times.toString())
                        .setText(R.id.tvStep, item.heartrate.toString())
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(context, 1)
        adapter.emptyView = getEmptyView(act, R.mipmap.ic_empty_pre,"佩戴手环切换至心率位置，等待10秒获取数据！")
    }

    fun initData() {
        Bus.observe<BraceletDataEvent>().subscribe {
            if (isResumed) {
                act.runOnUiThread {
                    if (it.callback.mode == CallbackMode.HEART_RATE) {
                        var data = it.callback.data as HeartrateBean
                        if (data.heartrate != 0) {
                            list.add(0, data.getmList().first() as Heartrate)
                            adapter.notifyDataSetChanged()
                            tvStep.text = data.heartrate.toString()
                        }
                    }
                    if (BraceletData.instance.batteryBean != null){
                        tvBat.text = BraceletData.instance.batteryBean!!.percent.toString() + "%"
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }
}