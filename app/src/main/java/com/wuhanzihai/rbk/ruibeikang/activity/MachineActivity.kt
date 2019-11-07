package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemTen
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomSinglePicker
import kotlinx.android.synthetic.main.activity_machine.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

// 手动记录数据
class MachineActivity : AppCompatActivity() {
    private var type = 1
    private var list = mutableListOf<String>()
    private var list1 = mutableListOf<String>()

    private lateinit var listTag: MutableList<TagItem>
    private lateinit var adapter: BaseQuickAdapter<TagItem, BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_machine)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        type = intent.getIntExtra("type", 1)

        initView()

        initData()
    }

    private fun initView() {
        if (type == 1) {
            rlBloodPressure.visibility = View.VISIBLE
            llBloodPressure.visibility = View.VISIBLE
        }

        if (type == 2) {
            rlBloodSugar.visibility = View.VISIBLE
            llBloodSugar.visibility = View.VISIBLE
        }

        llBloodPressure.onClick {
            CustomSinglePicker(act, CustomSinglePicker.ResultHandler {
                tvXinlv.text = it
            })
                    .setData(list)
                    .setIsLoop(false)
                    .setSelected(list.size / 2)
                    .show()
        }
        tvHigh.onClick {
            CustomSinglePicker(act, CustomSinglePicker.ResultHandler {
                tvHigh.text = it
            }).setData(list)
                    .setIsLoop(false)
                    .setSelected(list.size / 2)
                    .show()
        }
        tvLow.onClick {
            CustomSinglePicker(act, CustomSinglePicker.ResultHandler {
                tvLow.text = it
            }).setData(list)
                    .setIsLoop(false)
                    .setSelected(list.size / 2)
                    .show()
        }
        tvText.onClick {
            CustomSinglePicker(act, CustomSinglePicker.ResultHandler {
                tvText.text = it
            }).setData(list1)
                    .setIsLoop(false)
                    .setSelected(list1.size / 2)
                    .show()
        }
        btSave.onClick {
            if (type == 1) {
                if (tvHigh.text.isEmpty() || tvHigh.text.isEmpty()) {
                    toast("数据不完整")
                    return@onClick
                }
                var tHigh = tvHigh.text.toString().toInt()
                var tLow = tvLow.text.toString().toInt()
                var tXinlv = 0
                if (tvXinlv.text.isNotEmpty()) {
                    tXinlv = tvXinlv.text.toString().toInt()
                }
                startActivity<BloodPreDataDetailActivity>("type" to type
                        , "data" to "${tHigh}/${tLow}"
                        , "rate" to tXinlv
                        , "state" to "手动录入")
            }

            if (type == 2) {
                if (tvText.text.isEmpty()) {
                    toast("数据不完整")
                    return@onClick
                }
                var value = tvText.text.toString().toDouble()
                var type1 = ""
                var time = -1

                for (i in 0 until listTag.size) {
                    if (listTag[i].isCheck) {
                        type1 = listTag[i].text
                        time = i
                    }
                }
                if (type1.isEmpty()) {
                    toast("请选择")
                    return@onClick
                }
                startActivity<BloodPreDataDetailActivity>("type" to type
                        , "data" to value.toString()
                        , "time" to time
                        , "typetext" to type1
                        , "state" to "手动录入")
            }
        }

        listTag = mutableListOf()
        listTag.add(TagItem("空腹"))
        listTag.add(TagItem("早餐后"))
        listTag.add(TagItem("午餐前"))
        listTag.add(TagItem("午餐后"))
        listTag.add(TagItem("晚餐前"))
        listTag.add(TagItem("晚餐后"))
        listTag.add(TagItem("已用药"))
        listTag.add(TagItem("未用药"))

        adapter = object : BaseQuickAdapter<TagItem, BaseViewHolder>(R.layout.item_data_tag, listTag) {
            override fun convert(helper: BaseViewHolder?, item: TagItem?) {
                helper!!.setText(R.id.tvText, item!!.text)
                helper.getView<TextView>(R.id.tvText).isSelected = item.isCheck
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 4)
        rvView.addItemDecoration(DividerItemTen(act))
        adapter.setOnItemClickListener { adapter, view, position ->
            if (!listTag[position].isCheck) {
                for (item in listTag) {
                    item.isCheck = false
                }
                listTag[position].isCheck = true
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun initData() {
        for (i in 40..160) {
            list.add(i.toString())
        }

        for (i in 20..200) {
            list1.add((i.toDouble() / 10).toString())
        }
    }

    inner class TagItem(text: String) {
        var text: String = text
        var isCheck = false
    }
}
