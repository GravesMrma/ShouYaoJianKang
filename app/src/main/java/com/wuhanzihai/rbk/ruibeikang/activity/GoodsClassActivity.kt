package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.Child
import com.wuhanzihai.rbk.ruibeikang.data.entity.ChildItem
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsClassBean
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemGoods
import com.wuhanzihai.rbk.ruibeikang.presenter.GoodsClassPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.GoodsClassView
import kotlinx.android.synthetic.main.activity_goods_class.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class GoodsClassActivity : BaseMvpActivity<GoodsClassPresenter>(), GoodsClassView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule())
                .build().inject(this)
        mPresenter.mView = this
    }

    private lateinit var listLeft: MutableList<GoodsClassBean>
    private lateinit var adapterLeft: BaseQuickAdapter<GoodsClassBean, BaseViewHolder>

    private lateinit var listRight: MutableList<GoodsClassBean>
    private lateinit var adapterRight: BaseQuickAdapter<GoodsClassBean, BaseViewHolder>

    private lateinit var dividerItemGoods: DividerItemGoods

    override fun onGoodsClassResult(result: MutableList<GoodsClassBean>) {
        listLeft.addAll(result)
        if (listLeft.isNotEmpty()) {
            listLeft.first().isCheck = true
        }
        adapterLeft.notifyDataSetChanged()

        listRight.add(result.first())
        adapterRight.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_class)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        dividerItemGoods = DividerItemGoods(act)
        listLeft = mutableListOf()
        adapterLeft = object : BaseQuickAdapter<GoodsClassBean, BaseViewHolder>(R.layout.item_goods_class, listLeft) {
            override fun convert(helper: BaseViewHolder?, item: GoodsClassBean?) {
                helper!!.setText(R.id.tvText, item!!.name)
                helper.getView<TextView>(R.id.tvText).isSelected = item.isCheck
            }
        }
        rvView.adapter = adapterLeft
        rvView.layoutManager = GridLayoutManager(act, 1)
        adapterLeft.setOnItemClickListener { _, _, position ->
            if (!listLeft[position].isCheck) {
                for (goodsClassBean in listLeft) {
                    goodsClassBean.isCheck = false
                }
            }
            listLeft[position].isCheck = !listLeft[position].isCheck
            adapterLeft.notifyDataSetChanged()

            if (listLeft[position].child.isEmpty()) {
                if (listLeft[position].id == 2) {
                    startActivity<SingleTravelActivity>()
                } else {
                    startActivity<HealthCareActivity>("fatherId" to listLeft[position].id, "title" to listLeft[position].name)
                }
            } else {
                listRight.clear()
                listRight.add(listLeft[position])
                adapterRight.notifyDataSetChanged()
            }
        }

        listRight = mutableListOf()
        adapterRight = object : BaseQuickAdapter<GoodsClassBean, BaseViewHolder>(R.layout.item_goods_class_detail, listRight) {
            override fun convert(helper: BaseViewHolder?, item: GoodsClassBean?) {
                helper!!.setText(R.id.tvName, item!!.name)
                var adapterI = object : BaseQuickAdapter<Child, BaseViewHolder>(R.layout.item_goods_class_detail_item, item.child) {
                    override fun convert(helper: BaseViewHolder?, item: Child?) {
                        helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item!!.pic)
                        helper.setText(R.id.tvText, item.name)
                    }
                }
                helper.getView<RecyclerView>(R.id.rvView).run {
                    adapter = adapterI
                    layoutManager = GridLayoutManager(act, 2)
                    if (itemDecorationCount != 0) {
                        removeItemDecoration(dividerItemGoods)
                    }
                    addItemDecoration(dividerItemGoods)
                }
                adapterI.setOnItemClickListener { _, _, position ->
                    startActivity<HealthCareActivity>("fatherId" to item.id,
                            "childId" to item.child[position].id, "title" to item.name)
                }
            }
        }
        rvView1.adapter = adapterRight
        rvView1.layoutManager = GridLayoutManager(act, 1)
    }

    private fun initData() {

        mPresenter.goodClass()

    }
}
