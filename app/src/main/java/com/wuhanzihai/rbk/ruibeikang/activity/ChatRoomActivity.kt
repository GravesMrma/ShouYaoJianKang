package com.wuhanzihai.rbk.ruibeikang.activity

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseTakePhotoActivity
import com.hhjt.baselibrary.zoomable.ImageShowActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.common.loadImage
import com.hhjt.baselibrary.ext.loadImage
import com.wuhanzihai.rbk.ruibeikang.data.entity.ChatBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.MsgBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.AddQuestionReq
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ChatRecordReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.ChatRoomPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ChatRoomView
import com.wuhanzihai.rbk.ruibeikang.widgets.CircleImageView
import kotlinx.android.synthetic.main.activity_chat_room.*
import com.wuhanzihai.rbk.ruibeikang.itemDiv.DividerItemTen
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import org.devio.takephoto.compress.CompressConfig
import org.devio.takephoto.model.TResult
import org.jetbrains.anko.act
import org.jetbrains.anko.startActivity
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class ChatRoomActivity : BaseTakePhotoActivity<ChatRoomPresenter>(), ChatRoomView {
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onChatResult(result: ChatBean) {
        if (result.status == 2 || result.status == 4 || result.status == 3) {
            if (result.doctor != null) {
                if (result.doctor.name.isNotEmpty()) {
//                    clView.visibility = View.VISIBLE
                    ivHead.loadImage(result.doctor.image)
                    tvName.text = result.doctor.name
                    tvTitle.setTitleText(result.doctor.name)
                    tvJob.text = result.doctor.hospital
//                    listTag.addAll(result.doctor.tags)
                    listTag.clear()
                    listTag.add(result.doctor.title)
                    listTag.add(result.doctor.clinic_name)
                    adapterTag.notifyDataChanged()

                    personId = result.person_id
                    imgUrl = result.doctor.image
                    tvRemind.text = "${result.remain_time.toInt()}小时或${result.remain_num}次对话后问题关闭"
                } else {
                    tvRemind.text = "在线接诊中，平均6分钟内回复"
                    tvTitle.setTitleText("医生分配中...")
                    ivHead.loadImage("https://test.chunyu.me/media/images/fc7d/8968fe2e5cb5?imageMogr2/thumbnail/150x")
                }

            } else {
                ivHead.loadImage("https://test.chunyu.me/media/images/fc7d/8968fe2e5cb5?imageMogr2/thumbnail/150x")
            }
        } else {
            ivHead.loadImage("https://test.chunyu.me/media/images/fc7d/8968fe2e5cb5?imageMogr2/thumbnail/150x")
        }

        if (list.size != result.question.size) {
            list.clear()
            list.addAll(result.question)
            for (bean in list) {
                if (bean.type == "image") {
                    bean.types = bean.types * 10 + bean.types
                }
            }
            adapter.notifyDataSetChanged()
            rvMsg.scrollToPosition(list.size - 1)
        }

        if (result.status == 2 || result.status == 4 || result.status == 3) {
            if (result.doctor != null) {
                if (result.doctor.name.isNotEmpty()) {
                    clView.visibility = View.VISIBLE
                }
            }
        }

        if (result.status == 2 && result.doctor != null && result.doctor.name.isNotEmpty()) {
            llEdit.visibility = View.VISIBLE
        }
        if (result.status == 2) {
            if (!isGetting) {
                getChatRoom()
            }
        }
        if (result.status == 4) {
            tvRemind.text = "问题次数已用尽"
            tvRemind.setBackgroundColor(ContextCompat.getColor(act, R.color.gray_99))
        }
        if (result.status == 3) {
            tvRemind.text = "对话时间已超过24小时"
            tvRemind.setBackgroundColor(ContextCompat.getColor(act, R.color.gray_99))
        }
    }

    override fun onSendSuccess() {
        list.clear()
        mPresenter.chatRecord(ChatRecordReq(orderId, stateId))
    }

    private lateinit var list: MutableList<MsgBean>
    private lateinit var adapter: BaseMultiItemQuickAdapter<MsgBean, BaseViewHolder>

    private lateinit var listTag: MutableList<String>
    private lateinit var adapterTag: TagAdapter<String>

    private var orderId = 0
    private var stateId = 0
    private var personId = 0
    private var imgUrl = ""
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        orderId = intent.getIntExtra("orderId", 0)
        stateId = intent.getIntExtra("stateId", 0)

        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        adapter = object : BaseMultiItemQuickAdapter<MsgBean, BaseViewHolder>(list) {
            init {
                addItemType(1, R.layout.row_send)
                addItemType(11, R.layout.row_send_img)
                addItemType(2, R.layout.row_receive)
                addItemType(22, R.layout.row_receive_img)
            }

            override fun convert(helper: BaseViewHolder?, item: MsgBean?) {
                helper!!.setText(R.id.tvTime, item!!.time)
                when (helper.itemViewType) {
                    1 -> {
                        helper.setText(R.id.tvMsg, item.text)
                    }
                    11 -> {
                        helper.getView<ImageView>(R.id.ivMsg).loadImage(item.file)
                        helper.getView<ImageView>(R.id.ivMsg).onClick {
                            startActivity<ImageShowActivity>("urls" to mutableListOf(item.file))
                        }
                    }
                    2 -> {
                        if (imgUrl.isNotEmpty()) {
                            helper.getView<CircleImageView>(R.id.ivHead).loadImage(imgUrl)
                        }
                        helper.setText(R.id.tvMsg, item.text)
                    }
                    22 -> {
                        if (imgUrl.isNotEmpty()) {
                            helper.getView<CircleImageView>(R.id.ivHead).loadImage(imgUrl)
                        }
                        helper.getView<ImageView>(R.id.ivMsg).loadImage(item.file)
                        helper.getView<ImageView>(R.id.ivMsg).onClick {
                            startActivity<ImageShowActivity>("urls" to mutableListOf(item.file))
                        }
                    }
                }
            }
        }
        rvMsg.adapter = adapter
        rvMsg.addItemDecoration(DividerItemTen(act))
        rvMsg.layoutManager = GridLayoutManager(act, 1)
        var view = layoutInflater.inflate(R.layout.item_head_chat, null)
        view.onClick {
            startActivity<ArchivesDetailActivity>("personId" to 12)
        }
        adapter.addHeaderView(view)

        listTag = mutableListOf()
        adapterTag = object : TagAdapter<String>(listTag) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_doctor_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        tfView.adapter = adapterTag

        llView.onClick {
            edContent.clearFocus()
        }
        edContent.setOnKeyBoardHideListener { keyCode ->
            // 0 收起键盘 1 获取焦点  2  失去焦点
            when (keyCode) {
                0 -> edContent.clearFocus()
                1 -> handler.postDelayed({
                    if (list.isNotEmpty()) {
                        rvMsg.scrollToPosition(list.size - 1)
                    }
                }, 300)

            }
        }
        edContent.onClick {
            if (list.isNotEmpty()) {
                handler.postDelayed({ rvMsg.scrollToPosition(list.size - 1) }, 300)
            }
        }

        ivSelect.onClick {
            ivSelect.isSelected = !ivSelect.isSelected
            if (ivSelect.isSelected) {
                llPic.visibility = View.VISIBLE
            } else {
                llPic.visibility = View.GONE
            }
        }
        rlTake.onClick {
            val perms = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            )
            if (EasyPermissions.hasPermissions(this, *perms)) {
                mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                createTempFile()
                mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
            } else {
                EasyPermissions.requestPermissions(act, "你好,选取需要获取摄像头权限和读写内存权限。你能允许吗?", 0, *perms)
            }
        }
        rlCar.onClick {
            val perms = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            )
            if (EasyPermissions.hasPermissions(this, *perms)) {
                mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                mTakePhoto.onPickMultiple(1)
            } else {
                EasyPermissions.requestPermissions(act, "你好,选取需要获取摄像头权限和读写内存权限。你能允许吗?", 0, *perms)
            }
        }

        tvSend.onClick {
            if (edContent.text!!.isNotEmpty()) {
                mPresenter.addQuestion(mutableListOf(), AddQuestionReq(edContent.text.toString(), orderId))
                edContent.text!!.clear()
            }
        }
        ivHead.onClick {
            startActivity<DoctorActivity>("orderId" to orderId)
        }

        edContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tvSend.isSelected = s!!.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initData() {
        mPresenter.chatRecord(ChatRecordReq(orderId, stateId))
    }

    override fun takeCancel() {

    }

    override fun takeSuccess(result: TResult?) {
        mPresenter.addQuestion(mutableListOf(File(result!!.image.compressPath)), AddQuestionReq("", orderId))

    }

    private var runnable = Runnable {
        getChatRoom()
    }

    private var isGetting = false
    private fun getChatRoom() {
        isGetting = true
        mPresenter.chatRecord(ChatRecordReq(orderId, stateId))
        handler.postDelayed(runnable, 30000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}
