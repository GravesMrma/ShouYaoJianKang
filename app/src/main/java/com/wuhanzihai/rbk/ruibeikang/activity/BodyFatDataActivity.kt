package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.hhjt.baselibrary.common.BaseConstant
import com.jaeger.library.StatusBarUtil
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getNowDataString
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.healthData.BodyFatData
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemBodyFatData
import kotlinx.android.synthetic.main.activity_body_fat_data.*
import org.jetbrains.anko.act

class BodyFatDataActivity : AppCompatActivity() {
    private lateinit var list: MutableList<ItemData>
    private lateinit var adapter: BaseQuickAdapter<ItemData, BaseViewHolder>

    private var data = ""
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body_fat_data)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        list.add(ItemData("BMI", "", "0.00", R.mipmap.tzzj_icon_bmi))
        list.add(ItemData("体脂率", "%", "0.00", R.mipmap.tzzj_icon_tzl))
        list.add(ItemData("肌肉重量", "kg", "0.00", R.mipmap.tzzj_icon_jrzl))
        list.add(ItemData("内脏脂肪等级", "", "0", R.mipmap.tzzj_icon_nzzfdj))
        list.add(ItemData("水分率", "%", "0.00", R.mipmap.tzzj_icon_sfl))
        list.add(ItemData("骨量", "kg", "0.00", R.mipmap.tzzj_icon_gl))
        list.add(ItemData("基础代谢", "", "0.00", R.mipmap.tzzj_icon_jcdx))
        list.add(ItemData("脂肪重量", "kg", "0.00", R.mipmap.tzzj_icon_zfzl))
        list.add(ItemData("肌肉含量", "%", "0.00", R.mipmap.tzzj_icon_jrhl))
        list.add(ItemData("骨骼肌重量", "kg", "0.00", R.mipmap.tzzj_icon_ggjzl))
        list.add(ItemData("蛋白质率", "%", "0.00", R.mipmap.tzzj_icon_dbzl))
        list.add(ItemData("皮下脂肪率", "%", "0.00", R.mipmap.tzzj_icon_pxzfl))

        adapter = object : BaseQuickAdapter<ItemData, BaseViewHolder>(R.layout.item_body_fat, list) {
            override fun convert(helper: BaseViewHolder?, item: ItemData?) {
                helper!!.setImageResource(R.id.ivImg, item!!.img)
                        .setText(R.id.tvText, item.name)
                        .setText(R.id.tvDate, item.data)
                        .setText(R.id.tvUnit, item.unit)
            }
        }

        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 2)
        rvView.addItemDecoration(DividerItemBodyFatData(act))
    }

    lateinit var bodyFatData: BodyFatData

    private fun initData() {
        if (intent.getIntExtra("cache", 0) == 1) {
            bodyFatData = Hawk.get(BaseConstant.BODYFAT_DATA)
        } else {
            data = intent.getStringExtra("data")
            val gson = Gson()
            bodyFatData = gson.fromJson(data, BodyFatData::class.java)
        }

        tvWeight.text = bodyFatData.details.weight.toString().substring(0,
                bodyFatData.details.weight.toString().indexOf("."))
        tvDouble.text = bodyFatData.details.weight.toString().substring(bodyFatData.details.weight.toString().indexOf(".") + 1)
        if (bodyFatData.code != 0) {
            handler.postDelayed({
                showTextDesc(act, "您未光脚称重，只测量到体重数据!")

            }, 500)
            return
        }

        bodyFatData.time = getNowDataString()
        Hawk.put(BaseConstant.BODYFAT_DATA, bodyFatData)

        list[0].data = bodyFatData.details.bmi.toString()
        list[1].data = bodyFatData.details.ratioOfFat.toString()
        list[2].data = bodyFatData.details.weightOfMuscle.toString()
        list[3].data = bodyFatData.details.levelOfVisceralFat.toString()
        list[4].data = bodyFatData.details.ratioOfWater.toString()
        list[5].data = bodyFatData.details.weightOfBone.toString()
        list[6].data = bodyFatData.details.bmr.toString()
        list[7].data = bodyFatData.details.weightOfFat.toString()
        list[8].data = bodyFatData.details.ratioOfMuscle.toString()
        list[9].data = bodyFatData.details.weightOfSkeletalMuscle.toString()
        list[10].data = bodyFatData.details.ratioOfProtein.toString()
        list[11].data = bodyFatData.details.ratioOfSubcutaneousFat.toString()
        adapter.notifyDataSetChanged()
    }

    inner class ItemData(var name: String, var unit: String, var data: String, var img: Int)
}
