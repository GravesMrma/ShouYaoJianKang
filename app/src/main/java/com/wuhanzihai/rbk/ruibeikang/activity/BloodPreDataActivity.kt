package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.healthData.BloodPre
import com.wuhanzihai.rbk.ruibeikang.data.entity.healthData.BloodPreData
import com.wuhanzihai.rbk.ruibeikang.data.entity.healthData.BloodSugar
import com.wuhanzihai.rbk.ruibeikang.data.entity.healthData.BloodSugarData
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_29_14
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemFourteen
import kotlinx.android.synthetic.main.activity_blood_pre_data.*
import kotlinx.android.synthetic.main.layout_blood_pre_empty.view.*
import kotlinx.android.synthetic.main.layout_blood_sug_empty.view.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

// 血压数据
class BloodPreDataActivity : AppCompatActivity() {


    private lateinit var dividerItemFourteen: DividerItemFourteen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_pre_data)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        dividerItemFourteen = DividerItemFourteen(act)

    }

    private fun initData() {
        if (intent.getIntExtra("type", 0) == 1) {
            tvTitle.setTitleText("血压数据")
            var list = mutableListOf<BloodPreData>()
            var data = Hawk.get<MutableList<BloodPreData>>(BaseConstant.BLOODPRE_DATA)
            if (data != null) {
                list.addAll(data)
                list.reverse()
            }
            for (data in list) {
                data.item.reverse()
            }
            var adapter = object : BaseQuickAdapter<BloodPreData, BaseViewHolder>(R.layout.item_body_fat_data, list) {
                override fun convert(helper: BaseViewHolder?, item: BloodPreData?) {
                    helper!!.setText(R.id.tvTime, item!!.time)
                    var adapter1 = object : BaseQuickAdapter<BloodPre, BaseViewHolder>(R.layout.item_body_fat_data_item, item.item) {
                        override fun convert(helper: BaseViewHolder?, item: BloodPre?) {
                            helper!!.setText(R.id.tvValue, "${item!!.high}/${item!!.low}")
                                    .setText(R.id.tvRate, "心率:${item.rate}次/每分钟")
                                    .setText(R.id.tvTime, item.time)
                                    .setText(R.id.tvDanwei, "mmHg")
                        }
                    }
                    helper.getView<RecyclerView>(R.id.rvData).run {
                        adapter = adapter1
                        layoutManager = GridLayoutManager(act, 1)
                        if (itemDecorationCount != 0) {
                            removeItemDecoration(dividerItemFourteen)
                        }
                        addItemDecoration(dividerItemFourteen)
                    }
                    adapter1.setOnItemClickListener { _, _, position ->
                        startActivity<BloodPreDataDetailReadActivity>("type" to 1,
                                "state" to item.item[position].state,
                                "data" to "${item.item[position].high}/${item.item[position].low}")
                    }
                }
            }
            rvView.adapter = adapter
            rvView.layoutManager = GridLayoutManager(act, 1)
            var emptyView = layoutInflater.inflate(R.layout.layout_blood_pre_empty,null,false)
            emptyView.tvBuy.onClick {
                startActivity<GoodsDetailActivity>("id" to 57)
            }
            emptyView.tvCheck.onClick {
                startActivity<BloodCheckActivity>("title" to "血压", "type" to 1)
            }
            adapter.emptyView = emptyView
            rvView.addItemDecoration(DividerItem14_29_14(act))
        }

        if (intent.getIntExtra("type", 0) == 2) {
            tvTitle.setTitleText("血糖数据")
            var list = mutableListOf<BloodSugarData>()
            var data = Hawk.get<MutableList<BloodSugarData>>(BaseConstant.BLOODSUG_DATA)
            if (data != null) {
                list.addAll(data)
                list.reverse()
            }
            for (data in list) {
                data.item.reverse()
            }
            var adapter = object : BaseQuickAdapter<BloodSugarData, BaseViewHolder>(R.layout.item_body_fat_data, list) {
                override fun convert(helper: BaseViewHolder?, item: BloodSugarData?) {
                    helper!!.setText(R.id.tvTime, item!!.time)
                    var adapter1 = object : BaseQuickAdapter<BloodSugar, BaseViewHolder>(R.layout.item_body_fat_data_item, item.item) {
                        override fun convert(helper: BaseViewHolder?, item: BloodSugar?) {
                            helper!!.setText(R.id.tvValue, "${item!!.value}")
                                    .setText(R.id.tvRate, "${item.type}")
                                    .setText(R.id.tvTime, item.time)
                                    .setText(R.id.tvDanwei, "mmol/L")
                        }
                    }
                    helper.getView<RecyclerView>(R.id.rvData).run {
                        adapter = adapter1
                        layoutManager = GridLayoutManager(act, 1)
                        if (itemDecorationCount != 0) {
                            removeItemDecoration(dividerItemFourteen)
                        }
                        addItemDecoration(dividerItemFourteen)
                    }
                    adapter1.setOnItemClickListener { _, _, position ->
                        startActivity<BloodPreDataDetailReadActivity>("type" to 2,
                                "state" to item.item[position].state,
                                "typetext" to item.item[position].type,
                                "data" to "${item.item[position].value}")
                    }
                }
            }
            rvView.adapter = adapter
            var emptyView = layoutInflater.inflate(R.layout.layout_blood_sug_empty,null,false)
            emptyView.tvBuy1.onClick {
                startActivity<GoodsDetailActivity>("id" to 58)
            }
            emptyView.tvCheck1.onClick {
                startActivity<BloodCheckActivity>("title" to "血糖", "type" to 2)
            }
            adapter.emptyView = emptyView
            rvView.layoutManager = GridLayoutManager(act, 1)
            rvView.addItemDecoration(DividerItem14_29_14(act))
        }
    }
}
