package com.wuhanzihai.rbk.ruibeikang.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.hhjt.baselibrary.ext.loadImage
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.hhjt.baselibrary.utils.DateUtils
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderItem
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.OrderPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.OrderView
import kotlinx.android.synthetic.main.activity_order_service_detail.*
import org.jetbrains.anko.act

class OrderServiceDetailActivity  : BaseMvpActivity<OrderPresenter>(), OrderView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onOrderResult(result: OrderBean) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_service_detail)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))


        initView()
        initData()
    }

    private fun initView() {

    }

    private fun initData() {
        var data = intent.getSerializableExtra("data") as OrderItem

        ivImg.loadImage(data.goodslist.first().pro_good_remark.image)
        tvName.text = data.goodslist.first().pro_good_remark.name
        tvSpec.text = data.goodslist.first().pro_good_remark.name
        tvPrice.text = data.sub_total

        tvNameText.text = data.address_user
        tvPhone.text = data.address_tel
        tvDate.text = DateUtils.longToString(data.add_time.toLong()*1000,"yyyy-MM-dd")

        tvSn.text = data.order_no
        tvPayTime.text = DateUtils.longToString(data.add_time.toLong()*1000,"yyyy-MM-dd  HH:mm")
    }
}