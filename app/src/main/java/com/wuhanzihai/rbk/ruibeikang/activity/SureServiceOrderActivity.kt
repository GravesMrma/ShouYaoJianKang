package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.AddressBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsBuyBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderDetailBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.SureOrderBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.CommitBuyGoodsReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.presenter.SureOrderPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SureOrderView
import kotlinx.android.synthetic.main.activity_sure_service_order.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SureServiceOrderActivity : BaseMvpActivity<SureOrderPresenter>(), SureOrderView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule())
                .build().inject(this)
        mPresenter.mView = this
    }

    override fun onDefAddress(result: AddressBean) {
        adrId = result.address_id
    }

    override fun onCommitOrderResult(result: OrderDetailBean) {
        var data = ""
        for (s in result.orderlist) {
            data = "$data$s,"
        }
        if (data.isNotEmpty()) {
            startActivity<PayActivity>("data" to data.substring(0, data.length - 1)
                    , "price" to result.countprice)
            finish()
        } else {
            toast("订单确认无数据")
        }
    }

    private var adrId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sure_service_order)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()
        initData()
    }

    private fun initView() {
        tvPay.onClick {
            mPresenter.commitBuyGoods(CommitBuyGoodsReq(
                    intent.getIntExtra("product_id", -1)
                    , intent.getIntExtra("skuId", -1)
                    , adrId
                    , 0
                    , "noremaker"))
//            startActivity<PayActivity>()
        }
    }

    private fun initData() {
        var data = intent.getSerializableExtra("data") as GoodsBuyBean
        ivImg.loadImage(data.product.image)
        tvName.text = data.product.name
        tvSpec.text = data.product.name
        tvPrice.text = data.product.price
        tvNum.text = "x${data.product.number}"
        tvMoney.text = data.product.price

        mPresenter.getDefAddress()
    }
}
