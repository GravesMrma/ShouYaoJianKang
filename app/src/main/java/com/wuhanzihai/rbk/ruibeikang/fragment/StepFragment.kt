package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.mltcode.blecorelib.bean.SportsBean
import com.android.mltcode.blecorelib.mode.CallbackMode
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.eightbitlab.rxbus.Bus
import com.hhjt.baselibrary.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.BraceletData
import com.wuhanzihai.rbk.ruibeikang.data.entity.SportItem
import com.wuhanzihai.rbk.ruibeikang.event.BraceletDataEvent
import kotlinx.android.synthetic.main.fragment_step.*
import org.jetbrains.anko.support.v4.act
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


/**
 * desc
 */
class StepFragment : Fragment() {

    private lateinit var adapter: BaseQuickAdapter<SportItem, BaseViewHolder>
    private lateinit var list: MutableList<SportItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_step, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        initData()
    }

    fun initView() {
        list = mutableListOf()
        if (BraceletData.instance.batteryBean != null) {
            tvBat.text = BraceletData.instance.batteryBean!!.percent.toString() + "%"
        }
        adapter = object : BaseQuickAdapter<SportItem, BaseViewHolder>(R.layout.item_step, list) {
            override fun convert(helper: BaseViewHolder, item: SportItem) {
                helper.setText(R.id.tvData, item.data)
                if (item.step.isEmpty()) {
                    helper.setText(R.id.tvStep, "暂无记录")
                    helper.setText(R.id.tvCra, "明天你要加油哟")
                } else {
                    helper.setText(R.id.tvStep, item.step + "步")
                    helper.setText(R.id.tvCra, "消耗了:" + item.calorie + "大卡")
                }
            }
        }

        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(context, 1)
        adapter.emptyView = getEmptyView(act, "暂无步数")
        val format = SimpleDateFormat("MM月dd日 EE")
        tvTime.text = format.format(Date(System.currentTimeMillis()))
    }

    fun initData() {
        for (i in 0..14) {
            val item = SportItem(getDays(i), AppPrefsUtils.getString(BaseConstant.BRACELET_TODAY_STEP + getDays(i)), AppPrefsUtils.getString(BaseConstant.BRACELET_TODAY_CAL + getDays(i)))
            if (AppPrefsUtils.getString(BaseConstant.BRACELET_TODAY_STEP + getDays(i)) != "") {
                list.add(item)
            }
        }
        adapter.notifyDataSetChanged()

        Bus.observe<BraceletDataEvent>().subscribe {
            if (isResumed) {
                act.runOnUiThread {
                    if (it.callback.mode == CallbackMode.SPORTS_DATA) {
                        var sportsBean = it.callback.data as SportsBean
                        tvStep.text = sportsBean.step.toString()
                    }
                    if (BraceletData.instance.batteryBean != null) {
                        tvBat.text = BraceletData.instance.batteryBean!!.percent.toString() + "%"
                    }
                }
            }
        }
    }

    private fun getDays(cut: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.time = Date(System.currentTimeMillis())
        calendar.add(Calendar.DATE, -cut)
        val date1 = calendar.time
        return sdf.format(date1)
    }
}
