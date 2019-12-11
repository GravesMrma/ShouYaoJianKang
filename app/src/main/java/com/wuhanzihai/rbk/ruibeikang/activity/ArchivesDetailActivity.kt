package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.adapter.MyTagAdapter
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
import com.wuhanzihai.rbk.ruibeikang.data.entity.ArchivesDetail
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ArchivesDetailReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.EditArchivesReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.ArchivesPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ArchivesView
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomDatePicker
import com.wuhanzihai.rbk.ruibeikang.widgets.CustomSinglePicker
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_archives_detail.*
import org.jetbrains.anko.act

class ArchivesDetailActivity : BaseMvpActivity<ArchivesPresenter>(), ArchivesView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onArchivesDetail(result: ArchivesDetail) {
        tvGuanxi.text = result.connections
        edName.setText(result.name)
        if (result.sex == 1) {
            mRgRecord.check(R.id.mRbMan)
        }
        if (result.sex == 2) {
            mRgRecord.check(R.id.mRbWoman)
        }
        tvBirthday.text = result.birthday
        tvHeight.text = "${result.heights}cm"
        tvWeight.text = "${result.weights}Kg"
        tvXiyan.text = result.smoke + "年"
        tvDrink.text = result.drink + "年"

        var list1 = result.msg1.split(",", "，", " ", "/")
        var index1 = mutableSetOf<Int>()
        for (i in this.list1.indices) {
            for (s in list1) {
                if (s == this.list1[i]) {
                    index1.add(i)
                }
            }
        }
        tfView1.adapter.setSelectedList(index1)

        var list2 = result.msg2.split(",", "，", " ", "/")
        var index2 = mutableSetOf<Int>()
        for (i in this.list2.indices) {
            for (s in list2) {
                if (s == this.list2[i]) {
                    index2.add(i)
                }
            }
        }
        tfView2.adapter.setSelectedList(index2)

        var list3 = result.msg3.split(",", "，", " ", "/")
        var index3 = mutableSetOf<Int>()
        for (i in this.list3.indices) {
            for (s in list3) {
                if (s == this.list3[i]) {
                    index3.add(i)
                }
            }
        }
        tfView3.adapter.setSelectedList(index3)

        var list4 = result.msg4.split(",", "，", " ", "/")
        var index4 = mutableSetOf<Int>()
        for (i in this.list4.indices) {
            for (s in list4) {
                if (s == this.list4[i]) {
                    index4.add(i)
                }
            }
        }
        tfView4.adapter.setSelectedList(index4)

        var list5 = result.msg5.split(",", "，", " ", "/") as MutableList
        for (s in list5) {
            if (s.isEmpty()){
                list5.remove(s)
            }
        }
        var index5 = mutableSetOf<Int>()
        for (i in this.list5.indices) {
            for (s in list5) {
                if (!this.list5.contains(s)){
                    this.list5.add(s)
                }
            }
        }
        for (i in this.list5.indices) {
            for (s in list5) {
                if (s == this.list5[i]) {
                    index5.add(i)
                }
            }
        }
        tfView5.adapter.setSelectedList(index5)
        tfView5.adapter.notifyDataChanged()

        var list6 = result.msg6.split(",", "，", " ", "/") as MutableList
        for (s in list6) {
            if (s.isEmpty()){
                list6.remove(s)
            }
        }
        var index6 = mutableSetOf<Int>()
        for (i in this.list6.indices) {
            for (s in list6) {
                if (!this.list6.contains(s)){
                    this.list6.add(s)
                }
            }
        }
        for (i in this.list6.indices) {
            for (s in list6) {
                if (s == this.list6[i]) {
                    index6.add(i)
                }
            }
        }
        tfView6.adapter.setSelectedList(index6)
        tfView6.adapter.notifyDataChanged()

        var list7 = result.msg7.split(",", "，", " ", "/") as MutableList
        for (s in list7) {
            if (s.isEmpty()){
                list7.remove(s)
            }
        }
        var index7 = mutableSetOf<Int>()
        for (i in this.list7.indices) {
            for (s in list7) {
                if (!this.list7.contains(s)){
                    this.list7.add(s)
                }
            }
        }
        for (i in this.list7.indices) {
            for (s in list7) {
                if (s == this.list7[i]) {
                    index7.add(i)
                }
            }
        }
        tfView7.adapter.setSelectedList(index7)
        tfView7.adapter.notifyDataChanged()

        var list8 = result.msg8.split(",", "，", " ", "/") as MutableList
        for (s in list8) {
            if (s.isEmpty()){
                list8.remove(s)
            }
        }
        var index8 = mutableSetOf<Int>()
        for (i in this.list8.indices) {
            for (s in list8) {
                if (!this.list8.contains(s)){
                    this.list8.add(s)
                }
            }
        }
        for (i in this.list8.indices) {
            for (s in list8) {
                if (s == this.list8[i]) {
                    index8.add(i)
                }
            }
        }
        tfView8.adapter.setSelectedList(index8)
        tfView8.adapter.notifyDataChanged()

        var list9 = result.msg9.split(",", "，", " ", "/") as MutableList
        for (s in list9) {
            if (s.isEmpty()){
                list9.remove(s)
            }
        }
        var index9 = mutableSetOf<Int>()

        for (i in this.list9.indices) {
            for (s in list9) {
                if (!this.list9.contains(s)){
                    this.list9.add(s)
                }
            }
        }
        for (i in this.list9.indices) {
            for (s in list9) {
                if (s == this.list9[i]) {
                    index9.add(i)
                }
            }
        }
        tfView9.adapter.setSelectedList(index9)
        tfView9.adapter.notifyDataChanged()
    }

    override fun onEditArchivesResult() {
        showTextDesc(act, "修改成功")
    }

    private var personId = 0
    private lateinit var adapter1:TagAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archives_detail)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        personId = intent.getIntExtra("personId", 0)

        initView()

        initData()
    }

    private var list1 = mutableListOf("不好", "正常", "良好", "优秀")
    private var list2 = mutableListOf("不好", "正常", "良好", "优秀")
    private var list3 = mutableListOf("未婚", "已婚", "丁克", "其他")
    private var list4 = mutableListOf("未生育", "备孕期", "怀孕中", "已生育")
    private var list5 = mutableListOf("暂无", "高血压", "糖尿病", "心脏病", "过敏性疾病", "哮喘", "白癜风", "癫痫")
    private var list6 = mutableListOf("暂无", "高血压", "糖尿病", "心脏病", "脑梗", "癌症", "白癜风", "癫痫", "哮喘", "近视")
    private var list7 = mutableListOf("暂无", "青霉素", "头孢类", "破伤风抗霉素", "普鲁卡因", "地卡因", "磺胺类", "维生素B1", "泛影葡胺")
    private var list8 = mutableListOf("暂无", "芒果", "牛奶", "坚果类", "海鲜", "花粉", "油漆", "动物皮毛", "化妆品")
    private var list9 = mutableListOf("低头族", "久坐", "久站", "强忍大小便", "喝酒", "熬夜", "跷二郎腿", "吸烟", "饭后卧床", "不常锻炼", "如厕玩手机", "不喜喝水")

    private fun initView() {
        tvTitle.setMoreTextAction {
            var msg1 = ""
            for (i in tfView1.selectedList) {
                msg1 = msg1 + list1[i] + ","
            }
            var msg2 = ""
            for (i in tfView2.selectedList) {
                msg2 = msg2 + list2[i] + ","
            }
            var msg3 = ""
            for (i in tfView3.selectedList) {
                msg3 = msg3 + list3[i] + ","
            }
            var msg4 = ""
            for (i in tfView4.selectedList) {
                msg4 = msg4 + list4[i] + ","
            }
            var msg5 = ""
            for (i in tfView5.selectedList) {
                msg5 = msg5 + list5[i] + ","
            }
            var msg6 = ""
            for (i in tfView6.selectedList) {
                msg6 = msg6 + list6[i] + ","
            }
            var msg7 = ""
            for (i in tfView7.selectedList) {
                msg7 = msg7 + list7[i] + ","
            }
            var msg8 = ""
            for (i in tfView8.selectedList) {
                msg8 = msg8 + list8[i] + ","
            }
            var msg9 = ""
            for (i in tfView9.selectedList) {
                msg9 = msg9 + list9[i] + ","
            }

            mPresenter.editDoctor(EditArchivesReq(personId, edName.text.toString(),
                    tvBirthday.text.toString(),
                    tvGuanxi.text.toString(),
                    1,
                    tvHeight.text.toString().replace("cm", ""),
                    tvWeight.text.toString().replace("Kg", ""),
                    tvXiyan.text.toString().replace("年", ""),
                    tvDrink.text.toString().replace("年", ""),
                    msg1,
                    msg2,
                    msg3,
                    msg4,
                    msg5 + edText5.text.toString(),
                    msg6 + edText6.text.toString(),
                    msg7 + edText7.text.toString(),
                    msg8 + edText8.text.toString(),
                    msg9 + edText9.text.toString()))
        }
        mRgRecord.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.mRbMan -> {
                }
                R.id.mRbWoman -> {
                    adapter1.setSelectedList(0,1)
                    adapter1.notifyDataChanged()
                }
            }
        }
        rlGuanxi.onClick {
            CustomSinglePicker(act) {
                tvGuanxi.text = it
            }.setData(mutableListOf("父子", "母子", "妻子", "儿子", "女儿", "孙子", "孙女")).setIsLoop(false).show()
        }
        rlBirthday.onClick {
            CustomDatePicker(act) {
                tvBirthday.text = it
            }.setIsLoop(false).showSpecificTime(false).show()
        }
        var height = mutableListOf<String>()
        for (i in 100..250) {
            height.add("${i}cm")
        }
        rlHeight.onClick {
            CustomSinglePicker(act) {
                tvHeight.text = it
            }.setData(height).setIsLoop(false).show()
        }
        var weight = mutableListOf<String>()
        for (i in 30..200) {
            weight.add("${i}Kg")
        }
        rlWeight.onClick {
            CustomSinglePicker(act) {
                tvWeight.text = it
            }.setData(weight).setIsLoop(false).show()
        }
        rlXiyan.onClick {
            CustomSinglePicker(act) {
                tvXiyan.text = it
            }.setData(mutableListOf("1年", "2年", "3年", "4年", "5年", "10年")).setIsLoop(false).show()
        }
        rvDrink.onClick {
            CustomSinglePicker(act) {
                tvDrink.text = it
            }.setData(mutableListOf("1年", "2年", "3年", "4年", "5年", "10年")).setIsLoop(false).show()
        }

//        adapter1 = MyTagAdapter(list1,act)
        adapter1 = object : TagAdapter<String>(list1) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        tfView1.adapter = adapter1

        tfView2.adapter = object : TagAdapter<String>(list2) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        tfView3.adapter = object : TagAdapter<String>(list3) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        tfView4.adapter = object : TagAdapter<String>(list4) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        tfView5.adapter = object : TagAdapter<String>(list5) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        tfView6.adapter = object : TagAdapter<String>(list6) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        tfView7.adapter = object : TagAdapter<String>(list7) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        tfView8.adapter = object : TagAdapter<String>(list8) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        tfView9.adapter = object : TagAdapter<String>(list9) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_archives_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
    }

    private fun initData() {
        mPresenter.archivesDetail(ArchivesDetailReq(personId))
    }
}
