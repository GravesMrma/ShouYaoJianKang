package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.data.entity.ShareBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.ShareItem
import com.wuhanzihai.rbk.ruibeikang.data.protocal.NoParamIdPageReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import com.wuhanzihai.rbk.ruibeikang.presenter.ShareRecordPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ShareRecordView
import kotlinx.android.synthetic.main.activity_share_record.*
import org.jetbrains.anko.act

class ShareRecordActivity : BaseMvpActivity<ShareRecordPresenter>(), ShareRecordView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onShareRecordResult(result: ShareBean) {
        srView.finish()
        list.addAll(result.list)
        adapter.notifyDataSetChanged()
        tvPerNumber.text = "${result.count}人"
    }

    private lateinit var list: MutableList<ShareItem>
    private lateinit var adapter: BaseQuickAdapter<ShareItem, BaseViewHolder>
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_record)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<ShareItem, BaseViewHolder>(R.layout.item_share_record, list) {
            override fun convert(helper: BaseViewHolder?, item: ShareItem?) {
                helper!!.setText(R.id.tvName, item!!.types_name)
                        .setText(R.id.tvTime, item.create_time)
                        .setText(R.id.tvState, item.types_title)
                        .setText(R.id.tvMoney, "¥ ${item.price}")


            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem14_14_14(act))
        adapter.emptyView = getEmptyView(act, R.mipmap.empty_share, "您暂未申请等级请向平台申请等级可享受更多权益~")

        srView.refresh({
            page = 1
            list.clear()
            initData()
        }, {
            page++
            initData()
        })
    }

    private fun initData() {
        mPresenter.shareRecord(NoParamIdPageReq(page))
    }
}
