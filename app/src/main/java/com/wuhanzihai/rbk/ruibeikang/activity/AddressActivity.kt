package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.AddressBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodBean
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemAddress
import com.wuhanzihai.rbk.ruibeikang.presenter.AddressPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.AddressView
import kotlinx.android.synthetic.main.activity_address.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

// 地址列表页面 收货地址
class AddressActivity : BaseMvpActivity<AddressPresenter>(), AddressView {


    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onAddressResult() {
    }

    override fun onAddressListResult(result: MutableList<AddressBean>) {
        list.addAll(result)
        adapter.notifyDataSetChanged()
    }

    override fun onDefAddressResult(result: AddressBean) {
    }

    override fun onAddressInfoResult(result: AddressBean) {
    }

    override fun onUpAddressResult() {
    }

    private lateinit var list: MutableList<AddressBean>
    private lateinit var adapter: BaseQuickAdapter<AddressBean, BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()

        adapter = object : BaseQuickAdapter<AddressBean, BaseViewHolder>(R.layout.item_address, list) {
            override fun convert(helper: BaseViewHolder?, item: AddressBean?) {
                helper!!.setText(R.id.tvName, item!!.consignee)
                        .setText(R.id.tvPhone, item.mobile)
                        .setText(R.id.tvAddress, item.province + item.city + item.district + item.address)

                helper.setVisible(R.id.tvTag, item.is_default == 1)
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemAddress(act))

        srView.setOnRefreshListener {
            list.clear()
            initData()
        }
        srView.setOnLoadMoreListener {
            list.clear()
            initData()
        }

        tvAddAddress.onClick {
            startActivity<AddAddressActivity>()
        }
    }

    private fun initData() {
        mPresenter.getAddressList()
    }
}
