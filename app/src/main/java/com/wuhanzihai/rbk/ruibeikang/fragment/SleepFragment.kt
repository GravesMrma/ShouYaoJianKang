package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.android.mltcode.blecorelib.bean.Sleep
import com.android.mltcode.blecorelib.mode.SleepMode
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.eightbitlab.rxbus.Bus
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.BraceletData
import com.wuhanzihai.rbk.ruibeikang.event.BraceletDataEvent
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import kotlinx.android.synthetic.main.fragment_sleep.*
import org.jetbrains.anko.support.v4.act

import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.ceil


class SleepFragment : Fragment() {

    private var width = 0

    private lateinit var adapter: BaseQuickAdapter<Sleep, BaseViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sleep, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        width = MyUtils.getWidth(act)

        initView()

        initData()
    }

    fun initView() {
        val format = SimpleDateFormat("MM月dd日 EE")
        tvTime.text = format.format(Date(System.currentTimeMillis()))
        tvStep.text = toMut().toString() + ""

        if (BraceletData.instance.batteryBean != null){
            tvBat.text = BraceletData.instance.batteryBean!!.percent.toString() + "%"
        }
        if (BraceletData.instance.sleepBean != null) {
            val sleepTimeSum = BraceletData.instance.sleepBean!!.sleepTimeSum
            if (sleepTimeSum.contains(":")) {
                tvH.text = sleepTimeSum.substring(0, sleepTimeSum.indexOf(":"))
                tvM.text = sleepTimeSum.substring(sleepTimeSum.indexOf(":") + 1)
            }
            val sDeepSleepTime = BraceletData.instance.sleepBean!!.deepSleepTime
            if (sDeepSleepTime.contains(":")) {
                tvDh.text = sDeepSleepTime.substring(0, sDeepSleepTime.indexOf(":"))
                tvDm.text = sDeepSleepTime.substring(sDeepSleepTime.indexOf(":") + 1)
            }
            val sLightSleepTime = BraceletData.instance.sleepBean!!.lightSleepTime
            if (sLightSleepTime.contains(":")) {
                tvLh.text = sLightSleepTime.substring(0, sLightSleepTime.indexOf(":"))
                tvLm.text = sLightSleepTime.substring(sLightSleepTime.indexOf(":") + 1)
            }
            val sWakeupTime = BraceletData.instance.sleepBean!!.wakeupTime
            if (sWakeupTime.contains(":")) {
                tvWh.text = sWakeupTime.substring(0, sWakeupTime.indexOf(":"))
                tvWm.text = sWakeupTime.substring(sWakeupTime.indexOf(":") + 1)
            }
            tvTstaion.text = BraceletData.instance.sleepBean!!.date + "睡眠时间    " + getTime(BraceletData.instance.sleepBean!!.startTime) + "~" + getTime(BraceletData.instance.sleepBean!!.endTime)
            tvStrTime.text = getTime(BraceletData.instance.sleepBean!!.startTime)
            tvEndTime.text = getTime(BraceletData.instance.sleepBean!!.endTime)

            val startTime = getTime(BraceletData.instance.sleepBean!!.startTime)
            if (Integer.parseInt(startTime.substring(0, startTime.indexOf(":"))) in 9..22) {
                tvDecs.text = "首要博士提醒您:您在夜间11点前就入睡了,习惯非常好哦,一个良好的睡眠习惯对人体新陈代谢以及免疫力提升都有很大帮助,早点睡，睡得沉才是万能补药!"
            } else {
                val strMsg = "入睡太晚:您的入睡时间超过夜间11点,较晚入睡以及长期熬夜会降低人体免疫力,加速身体衰老,想保持身体年轻、增强免疫力,请到<font color=\"#5954BB\">健康商城购</font>购买--->综合免疫植物细胞营养素!"
                tvDecs.text = Html.fromHtml(strMsg)
            }
        }
    }

    fun initData() {
        if (BraceletData.instance.sleepBean!=null){
            adapter = object : BaseQuickAdapter<Sleep, BaseViewHolder>(R.layout.item_sleep_time, BraceletData.instance.sleepBean!!.sleepList) {
                override fun convert(helper: BaseViewHolder, item: Sleep) {
                    val imageView = helper.getView<ImageView>(R.id.ivImg)
                    val params = imageView.layoutParams as LinearLayout.LayoutParams
                    var a = item.endTime - item.startTime
                    a /= 1000
                    a /= 60 // 分钟

                    params.width = ceil(a.toDouble() / toMut().toDouble() * width).toInt()
                    imageView.layoutParams = params
                    if (item.mode == SleepMode.DEEP) {
                        imageView.setBackgroundColor(context!!.resources.getColor(R.color.deep))
                    }
                    if (item.mode == SleepMode.LIGHT) {
                        imageView.setBackgroundColor(context!!.resources.getColor(R.color.light))
                    }
                    if (item.mode == SleepMode.WAKE) {
                        imageView.setBackgroundColor(context!!.resources.getColor(R.color.wake))
                    }
                }
            }
            rvView.adapter = adapter
            rvView.layoutManager = StaggeredGridLayoutManager(1, RecyclerView.HORIZONTAL)
        }

        Bus.observe<BraceletDataEvent>().subscribe {
            if (isResumed) {
                act.runOnUiThread {
                    if (BraceletData.instance.batteryBean != null){
                        tvBat.text = BraceletData.instance.batteryBean!!.percent.toString() + "%"
                    }
                }
            }
        }
    }

    private fun toMut(): Int {
        if (BraceletData.instance.sleepBean == null) {
            return 0
        }
        val str = BraceletData.instance.sleepBean!!.sleepTimeSum
        val a = Integer.parseInt(str.substring(0, str.indexOf(":"))) * 60
        val b = Integer.parseInt(str.substring(str.indexOf(":") + 1, str.length))
        return a + b
    }

    private fun getTime(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm")
        return simpleDateFormat.format(Date(time))
    }
}
