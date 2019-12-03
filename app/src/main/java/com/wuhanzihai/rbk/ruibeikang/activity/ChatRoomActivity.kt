package com.wuhanzihai.rbk.ruibeikang.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hhjt.baselibrary.ui.activity.BaseTakePhotoActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.ChatBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.MsgBean
import com.wuhanzihai.rbk.ruibeikang.data.protocal.ChatRecordReq
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerUserComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.UserModule
import com.wuhanzihai.rbk.ruibeikang.presenter.ChatRoomPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.ChatRoomView
import kotlinx.android.synthetic.main.activity_chat_room.*
import org.jetbrains.anko.act

class ChatRoomActivity : BaseTakePhotoActivity<ChatRoomPresenter>(),ChatRoomView{
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent)
                .userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onChatResult(result: ChatBean) {

    }

    private lateinit var list: MutableList<MsgBean>
    private lateinit var adapter: BaseMultiItemQuickAdapter<MsgBean, BaseViewHolder>

    private var orderId = 0
    private var stateId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))


        initView()

        initData()
    }

    private fun initView() {
        list = mutableListOf()
        list.add(MsgBean(2, "你好啊"))
        list.add(MsgBean(1, "你好啊"))
        list.add(MsgBean(2, "嗯  你好"))
        list.add(MsgBean(1, "周末去钓鱼吗"))
        list.add(MsgBean(2, "好啊 你带鱼竿 我待遇而 怎么样啊"))
        list.add(MsgBean(1, "滚 我不带你去了"))
        list.add(MsgBean(1, "滚 "))
        list.add(MsgBean(2, "你别啊 "))
        list.add(MsgBean(2, "我真的想去好吧 "))
        list.add(MsgBean(1, "滚  快点"))

        adapter = object : BaseMultiItemQuickAdapter<MsgBean, BaseViewHolder>(list) {
            init {
                addItemType(1, R.layout.row_send)
                addItemType(2, R.layout.row_receive)
            }

            override fun convert(helper: BaseViewHolder?, item: MsgBean?) {
                when (helper!!.itemViewType) {
                    1 -> {
                        helper.setText(R.id.tvMsg, item!!.name)
                    }
                    2 -> {
                        helper.setText(R.id.tvMsg, item!!.name)
                    }
                }
            }

        }

        rvMsg.adapter = adapter
        rvMsg.layoutManager = GridLayoutManager(act, 1)

        edContent.setOnKeyBoardHideListener { keyCode, _ ->
            Log.e("你你你好好好", keyCode.toString() + "---")
        }

    }

    private fun initData() {
        mPresenter.chatRecord(ChatRecordReq(1,1))
    }
}
