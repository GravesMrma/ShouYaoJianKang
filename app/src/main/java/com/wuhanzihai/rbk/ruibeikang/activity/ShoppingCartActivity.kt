package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.ProductItem
import com.wuhanzihai.rbk.ruibeikang.data.entity.ShoppingCartBean
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
import java.net.URLEncoder

class ShoppingCartActivity : BaseMvpActivity<ShoppingCartPresenter>(), ShoppingCartView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onShoppingCartListResult(result: MutableList<ShoppingCartBean>) {
        list.addAll(result)
        adapter.notifyDataSetChanged()
    }

    override fun onDoneCartResult() {
        startActivity<SureOrderActivity>("data" to param)
    }

    private lateinit var list: MutableList<ShoppingCartBean>
    private lateinit var adapter: BaseQuickAdapter<ShoppingCartBean, BaseViewHolder>
    private var param = "" // 确认订单需要的参数

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        initView()

        initData()
    }

    private fun initView() {
        tvCommit.isSelected = true

        list = mutableListOf()
        adapter = object : BaseQuickAdapter<ShoppingCartBean, BaseViewHolder>(R.layout.item_shopping_cart, list) {
            override fun convert(helper: BaseViewHolder?, item: ShoppingCartBean?) {
                var adapterItem = object : BaseQuickAdapter<ProductItem, BaseViewHolder>(R.layout.item_shopping_cart_item, item!!.product_list) {
                    override fun convert(helper: BaseViewHolder?, item: ProductItem?) {
                        helper!!.setText(R.id.tvName, item!!.productname)
                                .setText(R.id.tvDesc, item.skuname )
                                .setText(R.id.tvPrice, item.price )
                                .setText(R.id.tvNumber, "x${item.number}" )
                        helper.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item.product_image)
                        helper.getView<ImageView>(R.id.ivTag).isSelected = item.isCheck
                    }
                }
                helper!!.getView<RecyclerView>(R.id.rvView).run {
                    adapter = adapterItem
                    layoutManager = GridLayoutManager(act, 1)
                }
                adapterItem.setOnItemClickListener { _, _, position ->
                    list[helper.layoutPosition].product_list[position].isCheck = !item.product_list[position].isCheck
                    adapterItem.notifyDataSetChanged()
                }
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)

        tvCommit.onClick {
            for (item in list.first().product_list) {
                if (item.isCheck){
                    param = param + item.cartid + ","
                }
            }
            mPresenter.doneCart(DoneCartReq(URLEncoder.encode(param.substring(0,param.length-1),"UTF-8")))
        }
    }

    private fun initData() {
        mPresenter.getShoppingCartList()
    }
}
