package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.BraceletData
import com.wuhanzihai.rbk.ruibeikang.data.entity.healthData.*
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemHealthData
import com.wuhanzihai.rbk.ruibeikang.utils.BraceletManagerUtil
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import kotlinx.android.synthetic.main.activity_health_data.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class HealthDataActivity : AppCompatActivity() {
    private lateinit var listPre: MutableList<BloodPre>
    private lateinit var adapterPre: BaseQuickAdapter<BloodPre, BaseViewHolder>

    private lateinit var listSug: MutableList<BloodSugar>
    private lateinit var adapterSug: BaseQuickAdapter<BloodSugar, BaseViewHolder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_data)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        listPre = mutableListOf()
        adapterPre = object : BaseQuickAdapter<BloodPre, BaseViewHolder>(R.layout.item_health_bloodpre_item, listPre) {
            override fun convert(helper: BaseViewHolder?, item: BloodPre?) {
                helper!!.setText(R.id.tvHeartPre, "${item!!.high} / ${item.low}")
                        .setText(R.id.tvHeartRateTime, item.time)
                if (item.rate != 0) {
                    helper.setText(R.id.tvHeartRate, "心率: ${item.rate}次/分")
                }
            }
        }
        rvBloodPre.adapter = adapterPre
        rvBloodPre.layoutManager = GridLayoutManager(act, 1)
        rvBloodPre.addItemDecoration(DividerItemHealthData(act))
        adapterPre.setOnItemClickListener { _, _, position ->
            startActivity<BloodPreDataDetailReadActivity>("type" to 1,
                    "state" to listPre[position].state,
                    "data" to "${listPre[position].high}/${listPre[position].low}")
        }

        listSug = mutableListOf()
        adapterSug = object : BaseQuickAdapter<BloodSugar, BaseViewHolder>(R.layout.item_health_bloodsur_item, listSug) {
            override fun convert(helper: BaseViewHolder?, item: BloodSugar?) {
                helper!!.setText(R.id.tvHeartPre, "${item!!.value}")
                        .setText(R.id.tvHeartRateTime, item.time)
                        .setText(R.id.tvHeartRate, item.type)
            }
        }
        rvBloodSug.adapter = adapterSug
        rvBloodSug.layoutManager = GridLayoutManager(act, 1)
        rvBloodSug.addItemDecoration(DividerItemHealthData(act))
        adapterSug.setOnItemClickListener { _, _, position ->
            startActivity<BloodPreDataDetailReadActivity>("type" to 2,
                    "state" to listSug[position].state,
                    "typetext" to listSug[position].type,
                    "data" to "${listSug[position].value}")
        }
    }

    private fun initData() {
        if (BraceletManagerUtil.instance.getBleDeviceState()) {
            llSportData.visibility = View.VISIBLE
            clSportData.visibility = View.GONE
            if (BraceletData.instance.sportsBean != null) {
                tvStep.text = BraceletData.instance.sportsBean!!.step.toString()
            }
            if (BraceletData.instance.sleepBean != null) {
                tvSleepHour.text = MyUtils.toHour(BraceletData.instance.sleepBean!!.sleepTimeSum)
                tvSleepMin.text = MyUtils.toMui(BraceletData.instance.sleepBean!!.sleepTimeSum)
            }
            if (BraceletData.instance.heartRateBean != null) {
                tvHeartRate.text = BraceletData.instance.heartRateBean!!.heartrate.toString()
            }
        } else {
            llSportData.visibility = View.GONE
            clSportData.visibility = View.VISIBLE
        }

        var dataPre = Hawk.get<MutableList<BloodPreData>>(BaseConstant.BLOODPRE_DATA)
        if (dataPre == null) {
            clPreData.visibility = View.VISIBLE
        } else {
            llPreData.visibility = View.VISIBLE
            listPre.addAll(dataPre.last().item)
            listPre.reverse()
            for (i in 0 until listPre.size) {
                if (i > 2) {
                    listPre.removeAt(listPre.size - 1)
                }
            }
            adapterPre.notifyDataSetChanged()
        }

        var dataSug = Hawk.get<MutableList<BloodSugarData>>(BaseConstant.BLOODSUG_DATA)

        if (dataSug == null) {
            clSugData.visibility = View.VISIBLE
        } else {
            llSugData.visibility = View.VISIBLE
            listSug.addAll(dataSug.last().item)
            listSug.reverse()
            for (i in 0 until listSug.size) {
                if (i > 2) {
                    listSug.removeAt(listSug.size - 1)
                }
            }
            adapterSug.notifyDataSetChanged()
        }

        var dataFat = Hawk.get<BodyFatData>(BaseConstant.BODYFAT_DATA)

        if (dataFat == null) {
            clBodyFatData.visibility = View.VISIBLE
        } else {
            llBodyFatData.visibility = View.VISIBLE
        }

        tvConnect1.onClick {
            startActivity<BluetoothSetActivity>()
        }

        tvBuy2.onClick {
            startActivity<GoodsDetailActivity>("id" to 57)
        }
        tvConnect2.onClick {
            startActivity<BIMCheckActivity>(
                    "type" to 1
            )
        }
        tvBuy3.onClick {
            startActivity<GoodsDetailActivity>("id" to 58)
        }
        tvConnect3.onClick {
            startActivity<BIMCheckActivity>(
                    "type" to 2
            )
        }
        tvBuy4.onClick {
            startActivity<GoodsDetailActivity>("id" to 56)
        }
        tvConnect4.onClick {
            startActivity<BodyFatActivity>()
        }
        tvXueya.onClick {
            startActivity<BloodPreDataActivity>("type" to 1)
        }
        tvXTdata.onClick {
            startActivity<BloodPreDataActivity>("type" to 2)
        }

        rlIdiot1.onClick {
            startActivity<BodyFatDataActivity>("cache" to 1)
        }
        rlIdiot2.onClick {
            startActivity<BodyFatDataActivity>("cache" to 1)
        }
        rlIdiot3.onClick {
            startActivity<BodyFatDataActivity>("cache" to 1)
        }
        tvBodyfatData.onClick {
            startActivity<BodyFatActivity>()
        }
    }
}
