package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.ProductItem
import com.wuhanzihai.rbk.ruibeikang.data.entity.ShoppingCartBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DoneCartNumReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DoneCartReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.presenter.ShoppingCartPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ShoppingCartView
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast
import java.net.URLEncoder

class ShoppingCartActivity : BaseMvpActivity<ShoppingCartPresenter>(), ShoppingCartView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onShoppingCartListResult(result: MutableList<ShoppingCartBean>) {
        list.clear()
        list.addAll(result)
        adapter.notifyDataSetChanged()
        if (list.isNotEmpty()) {
            tvTitle.setMoreTextContext("管理")
            rlOP.visibility = View.VISIBLE
        }
    }

    override fun onDoneCartResult() {
        startActivity<SureOrderActivity>("data" to param)
        finish()
    }

    override fun onDoneCartNumResult() {

    }

    override fun onDeleteCartResult() {
        tvAllMoney.text = "0.00"

        initData()
    }

    private lateinit var list: MutableList<ShoppingCartBean>
    private lateinit var adapter: BaseQuickAdapter<ShoppingCartBean, BaseViewHolder>
    private var param = "" // 确认订单需要的参数
    private var isManager = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()
        initData()

    }

    private fun initView() {
        tvCommit.isSelected = true

        list = mutableListOf()
        adapter = object : BaseQuickAdapter<ShoppingCartBean, BaseViewHolder>(R.layout.item_shopping_cart, list) {
            override fun convert(helper: BaseViewHolder?, item: ShoppingCartBean?) {
                helper!!.setText(R.id.tvStore, item!!.storename)
                if (item.storetype == 0) {
                    helper.getView<ImageView>(R.id.ivTag).visibility = View.GONE
                    helper.getView<TextView>(R.id.tvTag).visibility = View.VISIBLE
                } else {
                    helper.getView<ImageView>(R.id.ivTag).visibility = View.VISIBLE
                    helper.getView<TextView>(R.id.tvTag).visibility = View.GONE
                }
                helper.getView<ImageView>(R.id.ivSelect).isSelected = item.isCheck

                var adapterItem = object : BaseQuickAdapter<ProductItem, BaseViewHolder>(R.layout.item_shopping_cart_item, item!!.product_list) {
                    override fun convert(helper: BaseViewHolder?, item: ProductItem?) {
                        if (item!!.isManager) {
                            helper!!.getView<ConstraintLayout>(R.id.vInfo).visibility = View.INVISIBLE
                            helper.getView<RelativeLayout>(R.id.vManager).visibility = View.VISIBLE
                        } else {
                            helper!!.getView<ConstraintLayout>(R.id.vInfo).visibility = View.VISIBLE
                            helper.getView<RelativeLayout>(R.id.vManager).visibility = View.INVISIBLE
                        }
                        helper.setText(R.id.tvName, item.productname)
                                .setText(R.id.tvDesc, item.skuname)
                                .setText(R.id.tvPrice, item.price)
                                .setText(R.id.tvNum, item.number.toString())
                                .setText(R.id.tvNumber, "x${item.number}")
                        helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.product_image)
                        helper.getView<ImageView>(R.id.ivTag).isSelected = item.isCheck
                        helper.addOnClickListener(R.id.ivReduce)
                                .addOnClickListener(R.id.ivAdd)
                    }
                }
                helper.getView<RecyclerView>(R.id.rvView).run {
                    adapter = adapterItem
                    layoutManager = GridLayoutManager(act, 1)
                }
                adapterItem.setOnItemClickListener { _, _, position ->
                    list[helper.layoutPosition].product_list[position].isCheck = !item.product_list[position].isCheck
                    adapterItem.notifyDataSetChanged()
                    checkAllSelect()
                    getAllMoney()
                }
                adapterItem.setOnItemChildClickListener { _, view, position ->
                    when (view.id) {
                        R.id.ivReduce -> {
                            if (list[helper.layoutPosition].product_list[position].number > 1) {
                                list[helper.layoutPosition].product_list[position].number--
                            }
                        }
                        R.id.ivAdd -> {
                            if (list[helper.layoutPosition].product_list[position].number < 99) {
                                list[helper.layoutPosition].product_list[position].number++
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

                helper.setOnClickListener(R.id.ivSelect) {
                    item.isCheck = !item.isCheck
                    helper.getView<ImageView>(R.id.ivSelect).isSelected = item.isCheck
                    for (bean in item.product_list) {
                        bean.isCheck = item.isCheck
                    }
                    adapterItem.notifyDataSetChanged()
                    checkAllSelect()
                    getAllMoney()
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        adapter.emptyView = getEmptyView(act, "暂无数据")

        tvCommit.onClick {
            param = ""
            for (item in list.first().product_list) {
                if (item.isCheck) {
                    param = param + item.cartid + ","
                }
            }
            if (param.isEmpty()) {
                toast("未选中商品")
                return@onClick
            }
            if (isManager) {
                mPresenter.deleteCart(DoneCartReq(param))
            } else {
                mPresenter.doneCart(DoneCartReq(param))
            }
        }

        tvTitle.setMoreTextAction {
            isManager = !isManager
            for (shoppingCartBean in list) {
                for (productItem in shoppingCartBean.product_list) {
                    productItem.isManager = isManager
                }
            }
            adapter.notifyDataSetChanged()

            if (isManager) {
                tvTitle.setMoreTextContext("完成")
                tvCommit.text = "删除"
            } else {
                tvTitle.setMoreTextContext("编辑")
                tvCommit.text = "去结算"
                var data = ""
                for (cartBean in list) {
                    for (item in cartBean.product_list) {
                        data = data + item.cartid + ":" + item.number + ";"
                    }
                }
                mPresenter.doneCartNum(DoneCartNumReq(data))
            }
        }

        tvAllSelect.onClick {

            if (!checkAllSelect()) {
                // 取消全选
                for (bean in list) {
                    for (item in bean.product_list) {
                        item.isCheck = false
                    }
                }
            } else {
                for (bean in list) {
                    for (item in bean.product_list) {
                        item.isCheck = true
                    }
                }
            }
            adapter.notifyDataSetChanged()
            checkAllSelect()
            getAllMoney()
        }
    }

    private fun checkAllSelect(): Boolean {
        for (bean in list) {
            for (item in bean.product_list) {
                if (!item.isCheck) {
                    list.first().isCheck = false
                    adapter.notifyDataSetChanged()
                    tvAllSelect.isSelected = false
                    tvAllSelect.text = "全选"
                    return true
                }
            }
        }
        list.first().isCheck = true
        adapter.notifyDataSetChanged()
        tvAllSelect.isSelected = true
        tvAllSelect.text = "取消全选"
        return false
    }

    private fun getAllMoney() {
        var allMoney = 0.00
        for (cartBean in list) {
            for (item in cartBean.product_list) {
                if (item.isCheck) {
                    allMoney += item.original_price.toDouble() * item.number
                }
            }
        }
        tvAllMoney.text = allMoney.toString()
    }

    private fun initData() {
        mPresenter.getShoppingCartList()
    }
}
