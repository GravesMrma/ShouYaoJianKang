package com.wuhanzihai.rbk.ruibeikang.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.AddressBean
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemAddress
import com.wuhanzihai.rbk.ruibeikang.presenter.AddressPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.AddressView
import kotlinx.android.synthetic.main.activity_address.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

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
        srView.finishRefresh()
        srView.finishLoadMore()
        list.addAll(result)
        adapter.notifyDataSetChanged()
    }

    override fun onDefAddressResult(result: AddressBean) {}
    override fun onAddressInfoResult(result: AddressBean) {}
    override fun onUpAddressResult() {}

    private lateinit var list: MutableList<AddressBean>
    private lateinit var adapter: BaseQuickAdapter<AddressBean, BaseViewHolder>
    private var isChose = false
    private var addId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        isChose = intent.getBooleanExtra("chose", false)
        addId = intent.getIntExtra("addId", 0)

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
                if (isChose) {
                    if (addId == item.address_id) {
                        helper.getView<ImageView>(R.id.ivSelect).visibility = View.VISIBLE
                    }
                }
                helper.addOnClickListener(R.id.ivEdit)
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItemAddress(act))
        adapter.emptyView = getEmptyView(act, "暂无地址")
        adapter.setOnItemClickListener { adapter, view, position ->
            if (isChose) {
                var intent = Intent()
                intent.putExtra("address", list[position])
                setResult(4444, intent)
                finish()
            } else {
                startActivityForResult<AddAddressActivity>(3333, "data" to list[position], "update" to true)
            }
        }
        adapter.setOnItemChildClickListener { adapter, view, position ->
            startActivityForResult<AddAddressActivity>(3333, "data" to list[position], "update" to true)
        }

        srView.setOnRefreshListener {
            list.clear()
            initData()
        }
        srView.setOnLoadMoreListener {
            list.clear()
            initData()
        }

        tvAddAddress.onClick {
            startActivityForResult<AddAddressActivity>(3333)
        }
    }

    private fun initData() {
        mPresenter.getAddressList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 4444) {
            list.clear()
            initData()
        }
    }
}
