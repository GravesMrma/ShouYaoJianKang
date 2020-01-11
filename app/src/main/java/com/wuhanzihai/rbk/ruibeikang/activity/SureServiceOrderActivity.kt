package com.wuhanzihai.rbk.ruibeikang.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showTextDesc
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
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class SureServiceOrderActivity : BaseMvpActivity<SureOrderPresenter>(), SureOrderView {
    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule())
                .build().inject(this)
        mPresenter.mView = this
    }

    override fun onDefAddress(result: AddressBean) {
        tvName.text = result.consignee
        tvPhone.text = result.mobile
        tvAddress.text = result.address
        adrId = result.address_id
        tvAddAddress.visibility = View.INVISIBLE
        clAddress.visibility = View.VISIBLE

        if (result.is_default == 1) {
            tvTag.visibility = View.VISIBLE
        } else {
            tvTag.visibility = View.GONE
        }
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
            if (adrId == 0) {
                showTextDesc(act, "请先添加收货地址")
            }else{
                mPresenter.commitBuyGoods(CommitBuyGoodsReq(
                        intent.getIntExtra("product_id", -1)
                        , intent.getIntExtra("skuId", -1)
                        , adrId
                        , 0
                        , "noremaker",""))
            }
        }

        tvAddAddress.onClick {
            startActivityForResult<AddressActivity>(2222, "chose" to true
                    , "addId" to adrId)
        }

        clAddress.onClick {
            startActivityForResult<AddressActivity>(2222, "chose" to true
                    , "addId" to adrId)
        }
    }

    private fun initData() {
        var data = intent.getSerializableExtra("data") as GoodsBuyBean
        ivImg.loadImage(data.product.image)
        tvSerName.text = data.product.name
        tvSpec.text = data.product.intro
        tvPrice.text = data.product.price
        tvNum.text = "x${data.product.number}"
        tvMoney.text = data.product.price

        mPresenter.getDefAddress()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == 4444) {
            var dataAdr = data!!.getSerializableExtra("address") as AddressBean
            tvName.text = dataAdr.consignee
            tvPhone.text = dataAdr.mobile
            tvAddress.text = dataAdr.province + dataAdr.city + dataAdr.district + dataAdr.address
            adrId = dataAdr.address_id
            tvAddAddress.visibility = View.INVISIBLE
            clAddress.visibility = View.VISIBLE
            if (dataAdr.is_default == 1) {
                tvTag.visibility = View.VISIBLE
            } else {
                tvTag.visibility = View.GONE
            }
        }
    }
}
