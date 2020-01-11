package com.wuhanzihai.rbk.ruibeikang.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hhjt.baselibrary.data.entity.BusinessSettleEntity
import com.hhjt.baselibrary.divider.ChoseImageDivider
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.adapter.ChoseImageAdapter
import com.hhjt.baselibrary.ui.fragment.BaseTakePhotoFragment
import com.hhjt.baselibrary.utils.DensityUtils
import com.hhjt.baselibrary.utils.KeyboardUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.activity.ArchivesActivity
import com.wuhanzihai.rbk.ruibeikang.activity.StandardWebActivity
import com.wuhanzihai.rbk.ruibeikang.data.entity.OrderIdBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.CreateDoctorReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.InterrogationPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.InterrogationView
import com.wuhanzihai.rbk.ruibeikang.utils.AES
import kotlinx.android.synthetic.main.fragment_interrogation.*
import org.devio.takephoto.model.TResult
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import java.io.File
import java.net.URLDecoder

class InterrogationFragment : BaseTakePhotoFragment<InterrogationPresenter>(), InterrogationView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onCreateQuestion(result: OrderIdBean) {
        startActivity<ArchivesActivity>("orderId" to result.order_id.toInt())
        activity!!.finish()
    }

    private lateinit var list: MutableList<BusinessSettleEntity>
    private lateinit var adapter: ChoseImageAdapter<BusinessSettleEntity>
    private var page = 1

    private lateinit var imgs: MutableList<File>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_interrogation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()

        initData()

        tvText.onClick {
            startActivity<ArchivesActivity>()
        }
    }

    private fun initView() {
        imgs = mutableListOf()
        list = mutableListOf()
        adapter = ChoseImageAdapter(DensityUtils.getWindowWidth(act) / 4, list)

        rvImg.layoutManager = GridLayoutManager(act, 3)
        rvImg.adapter = adapter
        rvImg.addItemDecoration(ChoseImageDivider(act))

        adapter.setOnItemClickListener(object : ChoseImageAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                if (list.size == position) {
                    KeyboardUtil.hideSoftInput(act)
                    limit = 3
                    checkPermission()
                }
            }
        })

        tvCommit.onClick {
            if (edContent.text.length>=10) {
                mPresenter.createQuestion(imgs, CreateDoctorReq(edContent.text.toString()))
            } else {
                toast("病情描述不少于10个字")
            }
        }

        tvInfo.onClick {
            startActivity<StandardWebActivity>("title" to "拍照规则"
                    , "data" to "http://api.hcjiankang.com/api/Web/article?id=818")
        }
    }

    private fun initData() {

    }

    override fun takeSuccess(result: TResult?) {
        for (image in result!!.images) {
            imgs.add(File(result.image.compressPath))
            list.add(BusinessSettleEntity(result.image.compressPath))
        }
        adapter.notifyDataSetChanged()
    }
}