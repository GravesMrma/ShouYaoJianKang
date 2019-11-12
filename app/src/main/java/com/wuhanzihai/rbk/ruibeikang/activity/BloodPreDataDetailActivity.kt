package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import com.hhjt.baselibrary.common.BaseConstant
import com.hhjt.baselibrary.ext.onClick
import com.jaeger.library.StatusBarUtil
import com.orhanobut.hawk.Hawk
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.common.getNowDataString
import com.wuhanzihai.rbk.ruibeikang.common.getNowTimeString
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.data.entity.healthData.BloodPre
import com.wuhanzihai.rbk.ruibeikang.data.entity.healthData.BloodPreData
import com.wuhanzihai.rbk.ruibeikang.data.entity.healthData.BloodSugar
import com.wuhanzihai.rbk.ruibeikang.data.entity.healthData.BloodSugarData
import com.wuhanzihai.rbk.ruibeikang.utils.MyUtils
import kotlinx.android.synthetic.main.activity_blood_pre_data_detail.*
import org.jetbrains.anko.act


//  血压数据解读
class BloodPreDataDetailActivity : AppCompatActivity() {
    private var type = -1
    private var value = ""
    private var state = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_pre_data_detail)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

    }

    private fun initView() {
        value = intent.getStringExtra("data") // 值
        state = intent.getStringExtra("state")  // 手动录入还是设备测量
        var rate = intent.getIntExtra("rate", 0)
        tvData.text = value
        type = intent.getIntExtra("type", -1)
        if (type == 1) {
            tvDw.text = "mmHg"
            rlData2.visibility = View.VISIBLE
            val scoreArray = value.split("/")
            var a = scoreArray[1].toInt()
            initData(a.toString())
            if (a in 60..79) {
                tvDesc.text = "您的血压基本正常，情况良好哦！建议观察一段时间，无其他情况请继续保持良好的心态、合理的膳食、戒烟限酒、适度的运动。"
            }
            if (a in 80..89) {
                tvDesc.text = "您的血压值有些偏高，临界高血压标准，建议持续观察一段时间您的血压，是否有头晕、头疼等不适，如无不适建议您加强运动，控制体重，低盐饮食，戒烟限酒、保持愉悦心情！"
            }
            if (a in 90..99) {
                tvDesc.text = "您的血压值已经处于1级高血压，建议您在服药控制血压的同时，调整膳食结构：低脂、低盐饮食；戒烟限酒；控制体重腰围；每周监测血压波动，不适时及时咨询医生，保持良好心态，坚持运动。"
            }
            if (a in 100..109) {
                tvDesc.text = "您的血压值已经处于2级高血压，处于高危人群，建议您在听从医生药物控制血压的同时，要关注您的靶器官功能，按时体检监测心脑血管，请勿匆忙变换体位，时刻预防心脑血管疾病的发生几率。调整膳食结构：低脂、低盐、低糖饮食；戒烟限酒；控制体重腰围；每周监测血压波动，不适时及时咨询医生，保持情绪平稳，避免剧烈活动。"
            }
            if (a < 60) {
                tvDesc.text = "您的血压测量值低于正常，若无明显症状建议随时观察血压波动，注意体位变换。若感到不适请及时咨询医生。注意调节饮食，加强营养，荤素兼吃，合理搭配膳食，保证摄入全面充足的营养物质，使体质从纤弱逐渐变得健壮，晚上睡觉将头部垫高，可减轻低血压症状。锻炼身体，增强体质。平时养成运动的习惯，培养开朗的个性，保证足够的睡眠、规律正常的生活。"
            }
            if (a > 110) {
                tvDesc.text = "您的血压值已经处于3级高血压，处于超高危人群，应时刻关注自己血压变化，建议您在积极配合医生治疗的同时，时刻关注您自身靶器官功能的变化，按时体检监测心脑血管，请勿匆忙变换体位，时刻预防心脑血管疾病的发生几率。调整膳食结构：低脂、低盐、低糖饮食；戒烟限酒；控制体重腰围；每周监测血压波动，不适时及时咨询医生，保持情绪平稳，避免剧烈活动，注意动作缓慢。"
            }
            var data = Hawk.get<MutableList<BloodPreData>>(BaseConstant.BLOODPRE_DATA)
            var result = BloodPreData(getNowDataString(), mutableListOf(BloodPre(scoreArray[0].toInt()
                    , scoreArray[1].toInt()
                    , rate
                    , state
                    , getNowTimeString())))
            // 数据为空的时候 直接插入数据  没的说
            if (data == null) {
                Hawk.put(BaseConstant.BLOODPRE_DATA, mutableListOf(result))
            } else {
                if (data.last().time == getNowDataString()) { // 是今天的数据
                    data.last().item.add(BloodPre(scoreArray[0].toInt()
                            , scoreArray[1].toInt()
                            , rate
                            , state
                            , getNowTimeString()))
                } else {  // 不是今天的数据
                    data.add(BloodPreData(getNowDataString(), mutableListOf(BloodPre(scoreArray[0].toInt()
                            , scoreArray[1].toInt()
                            , rate
                            , state
                            , getNowTimeString()))))
                }
                Hawk.put(BaseConstant.BLOODPRE_DATA, data)
            }
            tvTime.text = getNowDataString() + "    " + getNowTimeString() + "     \n" + state
        }
        if (type == 2) {
            tvDw.text = "mmol/L"
            rlData1.visibility = View.VISIBLE
            var type = intent.getStringExtra("typetext") // 什么时间段的测试 饭前饭后
            initData(value,type)
            if (type.indexOf("后")==-1){ // 饭前
                if (value.toDouble() in 2.0..3.8) {
                    tvDesc.text = "您的血糖偏低，请密切关注血糖变化情况，不适时及时补充糖的摄入，随时跟医生沟通方便其诊断并调整治疗方案。"
                }
                if (value.toDouble() in 3.9..6.1) {
                    tvDesc.text = "您的血糖控制情况良好，请随时关注血糖变化情况。定时定量饮食、适量运动、戒烟限酒、保持心理平衡！"
                }
                if (value.toDouble() in 6.2..6.9) {
                    tvDesc.text = "您的血糖偏高，疑似糖尿病前期，该阶段以生活方式干预为主，建议您每天少食多餐，以4-6餐为宜，每次定时定量，不能过饱，以免血糖太高！"
                }
                if (value.toDouble() in 7.0..20.0) {
                    tvDesc.text = "您的血糖偏高，未能控制在正常范围，请密切关注血糖变化情况，不适时随时跟医生沟通方便其诊断并调整药物治疗方案，调整饮食、运动情况。"
                }
            }else{ // 饭后
                if (value.toDouble() in 2.0..3.8) {
                    tvDesc.text = "您的血糖偏低，请密切关注血糖变化情况，不适时及时补充糖的摄入，随时跟医生沟通方便其诊断并调整治疗方案。"
                }
                if (value.toDouble() in 3.9..7.7) {
                    tvDesc.text = "您的血糖控制情况良好，请随时关注血糖变化情况。定时定量饮食、适量运动、戒烟限酒、保持心理平衡！"
                }
                if (value.toDouble() in 7.8..11.0) {
                    tvDesc.text = "您的血糖偏高，疑似糖尿病前期，该阶段以生活方式干预为主，建议您每天少食多餐，以4-6餐为宜，每次定时定量，不能过饱，以免血糖太高！"
                }
                if (value.toDouble() in 11.1..20.0) {
                    tvDesc.text = "您的血糖偏高，未能控制在正常范围，请密切关注血糖变化情况，不适时随时跟医生沟通方便其诊断并调整药物治疗方案，调整饮食、运动情况。"
                }
            }

            var data = Hawk.get<MutableList<BloodSugarData>>(BaseConstant.BLOODSUG_DATA)
            var result = BloodSugarData(getNowDataString(), mutableListOf(BloodSugar(value.toDouble()
                    , type
                    , state
                    , getNowTimeString())))
            // 第一次空数据的时候
            if (data == null) {
                Hawk.put(BaseConstant.BLOODSUG_DATA, mutableListOf(result))
            } else {
                if (data.last().time == getNowDataString()) { //当天有数据
                    data.last().item.add(BloodSugar(value.toDouble()
                            , type
                            , state
                            , getNowTimeString()))
                } else {
                    data.add(BloodSugarData(getNowDataString(), mutableListOf(BloodSugar(value.toDouble()
                            , type
                            , state
                            , getNowTimeString()))))
                }
                Hawk.put(BaseConstant.BLOODSUG_DATA, data)
            }
            tvTime.text = getNowDataString() + "    " + getNowTimeString() + "      ${type} \n" + state
        }
    }

    private fun initData(data: String) {
        var params = RelativeLayout.LayoutParams(rlHead.layoutParams)
        var value = data.toInt()
        var a = MyUtils.getViewHeight(rlData2)
        var b = MyUtils.getViewHeight(rlHead)
        var c = MyUtils.getWidth(act)

        //  默认加上这么多 就是齐边了
        var def = (c - a) / 2 - (b / 2)
        //  范围是  0+def .. a+def
        var a1 = a * (61.0 / 290.0)
        var a2 = a * (42.0 / 290.0)

        if (value in 20..59) {
            // 先算比例  ((value - 20).toDouble() / 40)  比例乘以区间值  再进行加减
            var left = ((value - 20).toDouble() / 40) * a1 + def
            params.leftMargin = left.toInt()
        }
        if (value in 60..79) {
            var left = ((value - 60).toDouble() / 20) * a2 + def + a1
            params.leftMargin = left.toInt()
        }
        if (value in 80..89) {
            var left = ((value - 80).toDouble() / 10) * a2 + def + a1 + a2
            params.leftMargin = left.toInt()
        }
        if (value in 90..99) {
            var left = ((value - 90).toDouble() / 10) * a2 + def + a1 + a2 + a2
            params.leftMargin = left.toInt()
        }
        if (value in 100..109) {
            var left = ((value - 100).toDouble() / 10) * a2 + def + a1 + a2 + a2 + a2
            params.leftMargin = left.toInt()
        }
        if (value in 110..150) {
            var left = ((value - 110).toDouble() / 40) * a1 + def + a1 + a2 + a2 + a2 + a2
            params.leftMargin = left.toInt()
        }
        rlHead.layoutParams = params
        ivHead.loadImage(GlobalBaseInfo.getBaseInfo()!!.head_pic)
    }

    private fun initData(data: String,type:String) {
        var params = RelativeLayout.LayoutParams(rlHead.layoutParams)
        var value = data.toDouble()
        var a = MyUtils.getViewHeight(rlData2)
        var b = MyUtils.getViewHeight(rlHead)
        var c = MyUtils.getWidth(act)

        //  默认加上这么多 就是齐边了
        var def = (c - a) / 2 - (b / 2)
        //  范围是  0+def .. a+def
        var a1 = a * (61.0 / 290.0)
        var a2 = a * (84.0 / 290.0)

        if (type.indexOf("后")==-1){ // 饭前
            if (value in 2.0..3.8) {
                // 先算比例  ((value - 20).toDouble() / 40)  比例乘以区间值  再进行加减
                var left = ((value - 2.0) / 1.8) * a1 + def
                params.leftMargin = left.toInt()
            }
            if (value in 3.9..6.1) {
                var left = ((value - 3.9) / 2.2) * a2 + def + a1
                params.leftMargin = left.toInt()
            }
            if (value in 6.2..6.9) {
                var left = ((value - 6.2) / 0.7) * a2 + def + a1 + a2
                params.leftMargin = left.toInt()
            }
            if (value in 7.0..20.0) {
                var left = ((value - 7.0) / 13.0) * a2 + def + a1 + a2 + a2
                params.leftMargin = left.toInt()
            }
        }else{
            if (value in 2.0..3.8) {
                // 先算比例  ((value - 20).toDouble() / 40)  比例乘以区间值  再进行加减
                var left = ((value - 2.0) / 1.8) * a1 + def
                params.leftMargin = left.toInt()
            }
            if (value in 3.9..7.7) {
                var left = ((value - 3.9) / 3.8) * a2 + def + a1
                params.leftMargin = left.toInt()
            }
            if (value in 7.8..11.0) {
                var left = ((value - 7.8) / 3.2) * a2 + def + a1 + a2
                params.leftMargin = left.toInt()
            }
            if (value in 11.1..20.0) {
                var left = ((value - 11.1) / 13.0) * a2 + def + a1 + a2 + a2
                params.leftMargin = left.toInt()
            }
        }

        rlHead.layoutParams = params
        ivHead.loadImage(GlobalBaseInfo.getBaseInfo()!!.head_pic)
    }

}
