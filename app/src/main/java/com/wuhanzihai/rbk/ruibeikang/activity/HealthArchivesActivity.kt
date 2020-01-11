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
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.showChoseText
import com.wuhanzihai.rbk.ruibeikang.data.entity.ArchivesBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderIdBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DelArchivesReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import com.wuhanzihai.rbk.ruibeikang.presenter.ArchivesPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.HealthArchivesPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ArchivesView
import com.wuhanzihai.rbk.ruibeikang.presenter.view.HealthArchivesView
import kotlinx.android.synthetic.main.activity_health_archives.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import java.text.SimpleDateFormat
import java.util.*

class HealthArchivesActivity : BaseMvpActivity<ArchivesPresenter>(), ArchivesView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onArchivesResult(result: MutableList<ArchivesBean>) {
        srView.finish()
        list.addAll(result)
        adapter.notifyDataSetChanged()
        if (list.isNotEmpty()) {
            rlEmpty.visibility = View.GONE
            srView.visibility = View.VISIBLE
        } else {
            rlEmpty.visibility = View.VISIBLE
            srView.visibility = View.GONE
        }
    }

    override fun onDelArchivesResult() {
        list.clear()
        initData()
    }

    private lateinit var list: MutableList<ArchivesBean>
    private lateinit var adapter: BaseQuickAdapter<ArchivesBean, BaseViewHolder>
    private var edit = false
    private var page = 1
    private var orderId = 0
    private var personId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_archives)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        tvTitle.setMoreTextAction {
            edit = !edit
            adapter.notifyDataSetChanged()
        }
        tvAdd.onClick {
            startActivityForResult<AddArchivesActivity>(1234)
        }

        list = mutableListOf()
        adapter = object : BaseQuickAdapter<ArchivesBean, BaseViewHolder>(R.layout.item_health_archives, list) {
            override fun convert(helper: BaseViewHolder?, item: ArchivesBean?) {
                helper!!.setText(R.id.tvName, "${item!!.name}")
                        .setText(R.id.tvType, "${item.connections}")
                        .setText(R.id.tvAge, "出生年月:  ${item.birthday}")
                if (item.sex == 1) {
                    helper.setText(R.id.tvSex, "性别:  男")
                }
                if (item.sex == 2) {
                    helper.setText(R.id.tvSex, "性别:  女")
                }
                if (edit) {
                    helper.getView<ImageView>(R.id.ivDel).visibility = View.VISIBLE
                    helper.getView<TextView>(R.id.tvDetail).visibility = View.GONE
                } else {
                    helper.getView<ImageView>(R.id.ivDel).visibility = View.GONE
                    helper.getView<TextView>(R.id.tvDetail).visibility = View.VISIBLE
                }
                helper.addOnClickListener(R.id.ivDel)
            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem14_14_14(act))
        adapter.setOnItemClickListener { _, _, position ->
            startActivityForResult<ArchivesDetailActivity>(1234,"personId" to list[position].person_id)
        }
        adapter.setOnItemChildClickListener { _, _, position ->
            showChoseText(act, "是否删除该档案？", "健康档案删除后无法恢复请谨慎删除!", "确定") {
                mPresenter.deleteArchives(DelArchivesReq(list[position].person_id, 2))
            }
        }

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
        mPresenter.archivesList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 4321) {
            list.clear()
            initData()
        }
    }
}
