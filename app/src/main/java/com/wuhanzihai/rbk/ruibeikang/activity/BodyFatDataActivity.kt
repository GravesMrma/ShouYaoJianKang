package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem12_10_12
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemBodyFatData
import kotlinx.android.synthetic.main.activity_body_fat_data.*
import org.jetbrains.anko.act

class BodyFatDataActivity : AppCompatActivity() {
    private lateinit var list: MutableList<ItemData>
    private lateinit var adapter: BaseQuickAdapter<ItemData,BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body_fat_data)


        initView()

        initData()
    }

    private fun initView(){
        list = mutableListOf()
        list.add(ItemData("BMI","","0.00",R.mipmap.tzzj_icon_bmi))
        list.add(ItemData("体脂率","%","0.00",R.mipmap.tzzj_icon_tzl))
        list.add(ItemData("肌肉重量","kg","0.00",R.mipmap.tzzj_icon_jrzl))
        list.add(ItemData("内脏脂肪等级","","0",R.mipmap.tzzj_icon_nzzfdj))
        list.add(ItemData("水分率","%","0.00",R.mipmap.tzzj_icon_sfl))
        list.add(ItemData("骨量","kg","0.00",R.mipmap.tzzj_icon_gl))
        list.add(ItemData("基础代谢","","0.00",R.mipmap.tzzj_icon_jcdx))
        list.add(ItemData("脂肪重量","kg","0.00",R.mipmap.tzzj_icon_zfzl))
        list.add(ItemData("肌肉含量","%","0.00",R.mipmap.tzzj_icon_jrhl))
        list.add(ItemData("骨骼肌重量","kg","0.00",R.mipmap.tzzj_icon_ggjzl))
        list.add(ItemData("蛋白质率","%","0.00",R.mipmap.tzzj_icon_dbzl))
        list.add(ItemData("皮下脂肪率","%","0.00",R.mipmap.tzzj_icon_pxzfl))

        adapter = object :BaseQuickAdapter<ItemData,BaseViewHolder>(R.layout.item_body_fat,list){
            override fun convert(helper: BaseViewHolder?, item: ItemData?) {
                helper!!.setImageResource(R.id.ivImg,item!!.img)
                        .setText(R.id.tvText,item.name)
                        .setText(R.id.tvDate,item.data)
                        .setText(R.id.tvUnit,item.unit)
            }
        }

        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act,2)
        rvView.addItemDecoration(DividerItemBodyFatData(act))
    }

    private fun initData(){

    }

    inner class ItemData(var name: String, var unit: String,var data: String,var img:Int)
}
