package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.StepBean
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemStep
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import kotlinx.android.synthetic.main.activity_step_rank.*
import org.jetbrains.anko.act
import android.widget.TextView
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.common.getNowDataString
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.widgets.CircleImageView
import org.jetbrains.anko.startActivity


class StepRankActivity : AppCompatActivity() {

    private lateinit var list: MutableList<StepBean>
    private lateinit var adapter: BaseQuickAdapter<StepBean, BaseViewHolder>
    private var peoples = mutableListOf<People>()

    inner class People(var name: String, var head: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_rank)
        StatusBarUtil.setTranslucentForImageView(act, 0, null)

        peoples.add(People("Captain", "http://www.hcjiankang.com/androidimg/pic_step0.png"))
        peoples.add(People("水清澈", "http://www.hcjiankang.com/androidimg/pic_step1.png"))
        peoples.add(People("小KKKK", "http://www.hcjiankang.com/androidimg/pic_step2.png"))
        peoples.add(People("枯塘", "http://www.hcjiankang.com/androidimg/pic_step3.png"))
        peoples.add(People("远方日落", "http://www.hcjiankang.com/androidimg/pic_step4.png"))
        peoples.add(People("草莓", "http://www.hcjiankang.com/androidimg/pic_step5.png"))
        peoples.add(People("哼", "http://www.hcjiankang.com/androidimg/pic_step6.png"))
        peoples.add(People("糖果", "http://www.hcjiankang.com/androidimg/pic_step7.png"))
        peoples.add(People("大魔王", "http://www.hcjiankang.com/androidimg/pic_step8.png"))
        peoples.add(People("帮主", "http://www.hcjiankang.com/androidimg/pic_step9.png"))
        peoples.add(People("说你是猪", "http://www.hcjiankang.com/androidimg/pic_step10.png"))

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<StepBean, BaseViewHolder>(R.layout.item_step_rank, list) {
            override fun convert(helper: BaseViewHolder?, item: StepBean?) {
                helper!!.setText(R.id.tvRank, (helper.layoutPosition + 3).toString())
                        .setText(R.id.tvName, item!!.name)
                        .setText(R.id.tvStep, item.step.toString())
                        .setText(R.id.tvCollect, item.collect.toString())
                helper.getView<TextView>(R.id.tvCollect).isSelected = item.isCollect
                helper.getView<CircleImageView>(R.id.ivHead).loadImage(item.headurl)
                helper.addOnClickListener(R.id.tvCollect)
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemStep(act))
        adapter.setOnItemChildClickListener { _, _, position ->
            //            list[position].isCollect = !list[position].isCollect
//            adapter.notifyDataSetChanged()
            data!![position + 3].isCollect = !data!![position + 3].isCollect
            if (data!![position + 3].isCollect) {
                data!![position + 3].collect++
            } else {
                data!![position + 3].collect--
            }
            Hawk.put(BaseConstant.STEP_DATA + getNowDataString(), data)
            adapter.notifyDataSetChanged()

        }
        ivBg.loadImage("http://www.hcjiankang.com/androidimg/ic_healthaa.png")

        ivBack.onClick { finish() }
        toStep.onClick { startActivity<BluetoothSetActivity>() }

        rlTitle.background.mutate().alpha = 0
        fake_status_bar.background.mutate().alpha = 0
        var index = (MyUtils.getWidth(act) / 2.5).toInt()
        var sy = 0
        ndView.viewTreeObserver.addOnScrollChangedListener {
            sy = ndView.scrollY
            if (sy in 0..index) {
                var aaa = ((sy.toFloat() / index.toFloat()) * 255).toInt()
                Log.e("透明质", aaa.toString())
                rlTitle.background.mutate().alpha = aaa
                fake_status_bar.background.mutate().alpha = aaa
                StatusBarUtil.setDarkMode(act)

            } else {
                rlTitle.background.mutate().alpha = 255
                fake_status_bar.background.mutate().alpha = 255
                StatusBarUtil.setLightMode(act)
            }
        }
    }

    private var data: MutableList<StepBean>? = null
    private var indexs = mutableMapOf<Int, Int>()
    private fun initData() {
        data = Hawk.get<MutableList<StepBean>>(BaseConstant.STEP_DATA + getNowDataString())
        var result = mutableListOf<StepBean>()
        if (data == null) {
            for (i in 0..10) {
                var a = (0..10).random()
                while (true) {
                    if (indexs.containsKey(a)) {
                        a = (0..10).random()
                    } else {
                        indexs[a] = a
                        break
                    }
                }
                result.add(StepBean((28000 - (2000 * i)..30000 - (2000 * i)).random()
                        , peoples[a].name
                        , (10..200).random()
                        , peoples[a].head
                        , false))
            }
            Hawk.put(BaseConstant.STEP_DATA + getNowDataString(), result)
            data = result
            list.addAll(data!!)
        } else {
            list.addAll(data!!)
        }
        ivHead.loadImage(data!![0].headurl)
        tvName.text = data!![0].name +"占领了封面"
        ivBg.loadImage(data!![0].headurl)

        ivImage1.loadImage(data!![0].headurl)
        tvStep1.text = data!![0].step.toString()
        tvName1.text = data!![0].name
        tvCollect1.text = data!![0].collect.toString()
        tvCollect1.isSelected = data!![0].isCollect
        tvCollect1.onClick {
            var position = 0
            data!![position].isCollect = !data!![position].isCollect
            if (data!![position].isCollect) {
                data!![position].collect++
            } else {
                data!![position].collect--
            }
            tvCollect1.text = data!![position].collect.toString()
            tvCollect1.isSelected = data!![position].isCollect
            Hawk.put(BaseConstant.STEP_DATA + getNowDataString(), data)
        }

        ivImage2.loadImage(data!![1].headurl)
        tvStep2.text = data!![1].step.toString()
        tvName2.text = data!![1].name
        tvCollect2.text = data!![1].collect.toString()
        tvCollect2.isSelected = data!![1].isCollect
        tvCollect2.onClick {
            var position = 1
            data!![position].isCollect = !data!![position].isCollect
            if (data!![position].isCollect) {
                data!![position].collect++
            } else {
                data!![position].collect--
            }
            tvCollect2.text = data!![position].collect.toString()
            tvCollect2.isSelected = data!![position].isCollect
            Hawk.put(BaseConstant.STEP_DATA + getNowDataString(), data)
        }

        ivImage3.loadImage(data!![2].headurl)
        tvStep3.text = data!![2].step.toString()
        tvName3.text = data!![2].name
        tvCollect3.text = data!![2].collect.toString()
        tvCollect3.isSelected = data!![2].isCollect
        tvCollect3.onClick {
            var position = 2
            data!![position].isCollect = !data!![position].isCollect
            if (data!![position].isCollect) {
                data!![position].collect++
            } else {
                data!![position].collect--
            }
            tvCollect3.text = data!![position].collect.toString()
            tvCollect3.isSelected = data!![position].isCollect
            Hawk.put(BaseConstant.STEP_DATA + getNowDataString(), data)
        }

        list.removeAt(0)
        list.removeAt(0)
        list.removeAt(0)
        adapter.notifyDataSetChanged()
    }
}
