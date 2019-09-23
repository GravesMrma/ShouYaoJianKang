package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.fragment.BaseMvpFragment
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.*
import com.wuhanzihai.rbk.ruibeikang.common.GlobalBaseInfo
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.MineServiceBean
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemTen
import com.wuhanzihai.rbk.ruibeikang.presenter.MinePresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.MineView
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity

class MineFragment : BaseMvpFragment<MinePresenter>(),MineView {


    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onUserInfoResult(result: LoginData) {
        GlobalBaseInfo.setBaseInfo(result)
        ivImg.loadImage(result.head_pic)
        tvName.text = result.nickname
        lvView.setScore(63f)
    }

    private lateinit var list: MutableList<String>
    private lateinit var adapter: BaseQuickAdapter<String, BaseViewHolder>

    private lateinit var serList: MutableList<MineServiceBean>
    private lateinit var serAdapter: BaseQuickAdapter<MineServiceBean, BaseViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()
    }

    private fun initView() {
        tvMore.onClick {
            startActivity<OrderActivity>()
        }
        lCoupon.onClick {
            startActivity<CouponActivity>()
        }
        ivSign.onClick {
            startActivity<SignActivity>()
        }
        ivSet.onClick {
            startActivity<SetActivity>()
        }


        serList = mutableListOf()
        serList.add(MineServiceBean("服务订单", R.mipmap.fw_mid_icon_dd))
        serList.add(MineServiceBean("体检报告", R.mipmap.fw_mid_icon_tjbg))
        serList.add(MineServiceBean("地址管理", R.mipmap.fw_mid_icon_dzgl))
        serList.add(MineServiceBean("实名认证", R.mipmap.fw_mid_icon_smrz))
        serList.add(MineServiceBean("我的评价", R.mipmap.fw_mid_icon_wdpj))
        serList.add(MineServiceBean("健康档案", R.mipmap.fw_mid_icon_jkda))
        serList.add(MineServiceBean("兑换中心", R.mipmap.fw_mid_icon_dhzx))
        serList.add(MineServiceBean("常见问题", R.mipmap.fw_mid_icon_cjwt))
        serList.add(MineServiceBean("在线客服", R.mipmap.fw_mid_icon_zxkf))
        serAdapter = object : BaseQuickAdapter<MineServiceBean, BaseViewHolder>(R.layout.item_mine_service, serList) {
            override fun convert(helper: BaseViewHolder?, item: MineServiceBean?) {
                helper!!.setImageResource(R.id.ivImg, item!!.res)
                        .setText(R.id.tvText, item.title)
            }
        }
        rvView.adapter = serAdapter
        rvView.layoutManager = GridLayoutManager(act, 5)
        rvView.addItemDecoration(DividerItemTen(act))
        serAdapter.setOnItemClickListener { _, _, position ->
            startActivity<AddressActivity>()
        }

        list = mutableListOf()
        list.add("")
        list.add("")
        list.add("")
        list.add("")
        list.add("")

        adapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_image_mine, list) {
            override fun convert(helper: BaseViewHolder?, item: String?) {

            }
        }
        dvView.adapter = adapter
//        dvView.layoutManager = GridLayoutManager(act, 1, RecyclerView.HORIZONTAL, false)

        dvView.setItemTransformer(ScaleTransformer.Builder()
                .setMaxScale(1f)
                .setMinScale(0.9f)
                .setPivotX(Pivot.X.CENTER) // CENTER is a default one
                .setPivotY(Pivot.Y.BOTTOM) // CENTER is a default one
                .build())
    }

    private fun initData() {

        mPresenter.getUserInfo()
    }
}