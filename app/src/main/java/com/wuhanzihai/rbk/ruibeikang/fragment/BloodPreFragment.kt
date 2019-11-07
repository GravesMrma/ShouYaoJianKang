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
import com.android.mltcode.blecorelib.bean.BloodPressure
import com.android.mltcode.blecorelib.mode.CallbackMode
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.eightbitlab.rxbus.Bus
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.BraceletData
import com.wuhanzihai.rbk.ruibeikang.event.BraceletDataEvent
import kotlinx.android.synthetic.main.fragment_blood.*
import kotlinx.android.synthetic.main.fragment_blood.rvView
import kotlinx.android.synthetic.main.fragment_blood.tvBat
import kotlinx.android.synthetic.main.fragment_blood.tvStep
import org.jetbrains.anko.support.v4.act
import java.text.SimpleDateFormat
import java.util.*

class BloodPreFragment : Fragment() {

    private lateinit var adapter: BaseQuickAdapter<BloodPressure, BaseViewHolder>
    private lateinit var list: MutableList<BloodPressure>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bloodpre, null)
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
        adapter = object : BaseQuickAdapter<BloodPressure, BaseViewHolder>(R.layout.item_bloodpre, list) {
            override fun convert(helper: BaseViewHolder?, item: BloodPressure?) {
                helper!!.setText(R.id.tvData, item!!.date + "    ${helper.layoutPosition + 1}次测量")
                        .setText(R.id.tvStep, item.low.toString() + "/" + item.high.toString())
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(context, 1)
        adapter.emptyView = getEmptyView(act, "佩戴手环切换至血压位置，等待10秒获取数据！")
    }

    fun initData() {
        Bus.observe<BraceletDataEvent>().subscribe {
            if (isResumed) {
                act.runOnUiThread {
                    if (it.callback.mode == CallbackMode.BLOOD_PRESSURE) {
                        if ((it.callback.data as List<BloodPressure>).size == 1) {
                            var data = (it.callback.data as List<BloodPressure>).first()
                            if (data.timestamps != 0) {
                                if (list.size < 3) {
                                    list.add(0, data)
                                }
                                adapter.notifyDataSetChanged()
                                tvStep.text = data.low.toString() + "/" + data.high.toString()
                            }
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