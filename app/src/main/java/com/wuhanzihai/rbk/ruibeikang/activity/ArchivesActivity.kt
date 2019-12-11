package com.wuhanzihai.rbk.ruibeikang.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.finish
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ext.refresh
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.getEmptyView
import com.wuhanzihai.rbk.ruibeikang.common.showChoseText
import com.wuhanzihai.rbk.ruibeikang.data.entity.ArchivesBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderIdBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ChosePeopleReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.DelArchivesReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItem14_14_14
import com.wuhanzihai.rbk.ruibeikang.presenter.ArchivesPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ArchivesView
import kotlinx.android.synthetic.main.activity_archives.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class ArchivesActivity : BaseMvpActivity<ArchivesPresenter>(), ArchivesView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onArchivesResult(result: MutableList<ArchivesBean>) {
        srView.finish()
        list.addAll(result)
        adapter.notifyDataSetChanged()
    }

    override fun onDelArchivesResult() {
        page = 1
        list.clear()
        initData()
    }

    override fun onChosePeopleResult(result: OrderIdBean) {
        startActivity<PayInterrogationActivity>("orderId" to result.order_id.toInt())
        finish()
    }

    private lateinit var list: MutableList<ArchivesBean>
    private lateinit var adapter: BaseQuickAdapter<ArchivesBean, BaseViewHolder>
    private var edit = false
    private var page = 1
    private var orderId = 0
    private var personId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archives)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        orderId = intent.getIntExtra("orderId", 0)

        initView()

        initData()
    }

    private fun initView() {
        tvTitle.setMoreTextAction {
            edit = !edit
            adapter.notifyDataSetChanged()
        }

        list = mutableListOf()
        adapter = object : BaseQuickAdapter<ArchivesBean, BaseViewHolder>(R.layout.item_archives, list) {
            override fun convert(helper: BaseViewHolder?, item: ArchivesBean?) {
                helper!!.setText(R.id.tvName, "${item!!.name} (${item.connections})")
                if (item.sex == 1) {
                    helper.setText(R.id.tvSex, "男   ${item.birthday}")
                }
                if (item.sex == 2) {
                    helper.setText(R.id.tvSex, "女   ${item.birthday}")
                }
                if (edit) {
                    helper.getView<ImageView>(R.id.ivDelete).visibility = View.VISIBLE
                    helper.getView<ImageView>(R.id.ivSelect).visibility = View.GONE
                } else {
                    helper.getView<ImageView>(R.id.ivSelect).visibility = View.VISIBLE
                    helper.getView<ImageView>(R.id.ivDelete).visibility = View.GONE
                }
                helper.getView<ImageView>(R.id.ivSelect).isSelected = item.isCheck
                helper.addOnClickListener(R.id.ivDelete)

            }
        }
        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 1)
        rvView.addItemDecoration(DividerItem14_14_14(act))
        adapter.emptyView = getEmptyView(act,R.mipmap.empty_archives,"暂无档案，快速添加继续提问~")
        adapter.setOnItemClickListener { _, _, position ->
            if (!list[position].isCheck) {
                for (bean in list) {
                    bean.isCheck = false
                }
                list[position].isCheck = true
                personId = list[position].person_id
                adapter.notifyDataSetChanged()
            }
        }
        adapter.setOnItemChildClickListener { _, _, position ->
            showChoseText(act, "确认删除?") {
                mPresenter.deleteArchives(DelArchivesReq(list[position].person_id, 2))
            }
        }

        tvAdd.onClick {
            startActivityForResult<AddArchivesActivity>(1234)
        }

        tvNext.onClick {
            if (personId != 0) {
                mPresenter.chosePeople(ChosePeopleReq(personId, orderId, 1))
            } else {
                toast("选择档案")
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
        if (resultCode == 4321) {
            list.clear()
            initData()
        }
    }
}
