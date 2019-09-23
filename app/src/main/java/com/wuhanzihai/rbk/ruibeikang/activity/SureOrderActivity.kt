package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.facebook.drawee.view.SimpleDraweeView
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.Product
import com.wuhanzihai.rbk.ruibeikang.data.entity.Storedata
import com.wuhanzihai.rbk.ruibeikang.data.entity.SureOrderBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DoneCartReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemFifteen
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemFive
import com.wuhanzihai.rbk.ruibeikang.presenter.SureOrderPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SureOrderView
import kotlinx.android.synthetic.main.activity_sure_order.*
import org.jetbrains.anko.act

class SureOrderActivity : BaseMvpActivity<SureOrderPresenter>(), SureOrderView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule())
                .build().inject(this)
        mPresenter.mView = this
    }

    override fun onDoneCartResult(result: SureOrderBean) {
        list.addAll(result.storedata)
        adapter.notifyDataSetChanged()
    }

    private lateinit var list: MutableList<Storedata>
    private lateinit var adapter: BaseQuickAdapter<Storedata, BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sure_order)

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<Storedata, BaseViewHolder>(R.layout.item_order_sure, list) {
            override fun convert(helper: BaseViewHolder?, item: Storedata?) {
                Log.e("item!!.product_list", item!!.product_list.size.toString())

                helper!!.getView<RecyclerView>(R.id.rvView).run {
                    adapter = object : BaseQuickAdapter<Product, BaseViewHolder>(R.layout.item_order_sure_item, item.product_list) {
                        override fun convert(helper: BaseViewHolder?, item: Product?) {
//                            helper!!.getView<SimpleDraweeView>(R.id.ivImg).loadImage(item!!.product_image)
//                            helper.setText(R.id.tvName, item.productname)
//                                    .setText(R.id.tvSpec, item.skuname)
                            layoutManager = GridLayoutManager(act, 1)
//                            if (itemDecorationCount != 0) {
//                                removeItemDecoration(dividerItemFourteen)
//                            }
//                            addItemDecoration(dividerItemFourteen)
                        }

                    }
                }

                helper.setText(R.id.tvAllNum, "dasdasdasd")
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
//        rvView.addItemDecoration(DividerItemFive(act))
    }

    private fun initData() {
        var data = intent.getStringExtra("data")
        mPresenter.doneCart(DoneCartReq(data.substring(0, data.length - 1)))

    }
}
