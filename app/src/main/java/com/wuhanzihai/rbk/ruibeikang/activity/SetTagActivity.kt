package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.hhjt.baselibrary.utils.LoginUtils
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.LoginData
import com.wuhanzihai.rbk.ruibeikang.data.entity.MineAdv
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.SetSexPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SetSexView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_set_tag.*
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity

class SetTagActivity : BaseMvpActivity<SetSexPresenter>(), SetSexView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onSaveInfoResult() {

    }

    override fun onUserInfoResult(result: LoginData) {
        LoginUtils.saveLoginStatus(true, result.token, result.user_id)
    }

    override fun onTagWordsResult(result: MineAdv) {
        for (s in result.item) {
            list.add(TagItem(s,false))
        }
        adapter.notifyDataSetChanged()
    }

    private lateinit var list: MutableList<TagItem>
    private lateinit var adapter: BaseQuickAdapter<TagItem, BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_tag)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseQuickAdapter<TagItem, BaseViewHolder>(R.layout.item_tag, list) {
            override fun convert(helper: BaseViewHolder?, item: TagItem?) {
                helper!!.setText(R.id.tvText, item!!.tag)
                helper.getView<TextView>(R.id.tvText).isSelected = item.isCheck
            }
        }

        rvView.adapter = adapter
        rvView.layoutManager = GridLayoutManager(act, 3)
        adapter.setOnItemClickListener { _, _, position ->
            list[position].isCheck = !list[position].isCheck
            adapter.notifyDataSetChanged()
        }

        tvBack.onClick {
            finish()
        }

        tvNext.onClick {
            startActivity<MainActivity>()
            finish()
        }
    }

    private fun initData() {
        mPresenter.keyWords()
        mPresenter.getUserInfo()
    }

    inner class TagItem(var tag: String, var isCheck: Boolean)
}
