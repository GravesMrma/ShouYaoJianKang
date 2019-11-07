package com.wuhanzihai.rbk.ruibeikang.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.hhjt.baselibrary.ext.onClick
import com.hhjt.baselibrary.ui.activity.BaseMvpActivity
import com.jaeger.library.StatusBarUtil
import com.wuhanzihai.rbk.ruibeikang.R
import com.wuhanzihai.rbk.ruibeikang.data.entity.GoodsResult
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthClassBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.HealthListBean
import com.wuhanzihai.rbk.ruibeikang.data.entity.SearchBean
import com.wuhanzihai.rbk.ruibeikang.fragment.SearchClassFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.SearchGoodsFragment
import com.wuhanzihai.rbk.ruibeikang.fragment.SearchInfoFragment
import com.wuhanzihai.rbk.ruibeikang.injection.component.DaggerMallComponent
import com.wuhanzihai.rbk.ruibeikang.injection.module.MallModule
import com.wuhanzihai.rbk.ruibeikang.presenter.SearchPresenter
import com.wuhanzihai.rbk.ruibeikang.presenter.view.SearchView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.act
import java.util.*

class SearchActivity : BaseMvpActivity<SearchPresenter>(), SearchView {

    companion object {
        var keyWord = ""
    }

    override fun injectComponent() {
        DaggerMallComponent.builder().activityComponent(mActivityComponent)
                .mallModule(MallModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onSearchBean(result: SearchBean) {
        hotList.clear()
        hisList.clear()

        hotList.addAll(result.hotkeyword)
        hotAdapter.notifyDataChanged()

        hisList.addAll(result.userkeyword)
        hisAdapter.notifyDataChanged()
    }

    override fun onSearchGoodsBean(result: GoodsResult) {

    }

    override fun onHealthListResult(result: HealthListBean) {
    }

    override fun onHealthClassResult(result: HealthClassBean) {

    }

    private lateinit var hotList: MutableList<String>
    private lateinit var hotAdapter: TagAdapter<String>

    private lateinit var hisList: MutableList<String>
    private lateinit var hisAdapter: TagAdapter<String>

    private val mStack = Stack<Fragment>()
    private lateinit var pageAdapter: FragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        StatusBarUtil.setLightMode(act)
        StatusBarUtil.setColorNoTranslucent(act, ContextCompat.getColor(act, R.color.white))

        initView()

        initData()
    }

    private lateinit var searchInfoFragment: SearchInfoFragment
    private lateinit var searchClassFragment: SearchClassFragment
    private lateinit var searchGoodsFragment: SearchGoodsFragment

    private fun initView() {
        searchInfoFragment = SearchInfoFragment()
        searchClassFragment = SearchClassFragment()
        searchGoodsFragment = SearchGoodsFragment()
        mStack.add(searchInfoFragment)
        mStack.add(searchClassFragment)
        mStack.add(searchGoodsFragment)

        pageAdapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mStack[position]
            }

            override fun getCount(): Int {
                return mStack.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return null
            }
        }
        vpView.adapter = pageAdapter
        vpView.setNoFocus(false)
        vpView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}
            override fun onPageSelected(p0: Int) {
                when (p0) {
                    0 -> mRgRecord.check(R.id.mRbAll)
                    1 -> mRgRecord.check(R.id.mRbDfk)
                    2 -> mRgRecord.check(R.id.mRbDfw)
                }
            }
        })

        hotList = mutableListOf()
        hotAdapter = object : TagAdapter<String>(hotList) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_search_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        rvView.adapter = hotAdapter
        rvView.setOnTagClickListener { _, position, _ ->
            keyWord = hotList[position]
            edContent.setText(keyWord)
            searchWord()
            llView.visibility = View.GONE
            vpView.visibility = View.VISIBLE
            return@setOnTagClickListener false
        }

        hisList = mutableListOf()
        hisAdapter = object : TagAdapter<String>(hisList) {
            override fun getView(parent: FlowLayout, position: Int, t: String): View {
                var tv = LayoutInflater.from(act).inflate(
                        R.layout.item_search_tag,
                        parent,
                        false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        rvView1.adapter = hisAdapter
        rvView1.setOnTagClickListener { _, position, _ ->
            keyWord = hotList[position]
            edContent.setText(keyWord)
            searchWord()
            llView.visibility = View.GONE
            vpView.visibility = View.VISIBLE
            return@setOnTagClickListener false
        }

        edContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.isEmpty()) {
                    tvCommit.isSelected = true
                    tvCommit.text = "取消"
                    llView.visibility = View.VISIBLE
                    vpView.visibility = View.GONE
                } else {
                    tvCommit.isSelected = false
                    tvCommit.text = "搜索"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        tvCommit.onClick {
            if (tvCommit.text == "取消") {
                finish()
                return@onClick
            }
            var keyword = ""
            if (!tvCommit.isSelected) {
                keyword = edContent.text.toString()
            }
            keyWord = keyword
            searchWord()
            llView.visibility = View.GONE
            vpView.visibility = View.VISIBLE
        }

        mRgRecord.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.mRbAll -> {
                    mPresenter.searchInfoWords()
                    vpView.currentItem = 0
                }
                R.id.mRbDfk -> {
                    mPresenter.searchClassWords()
                    vpView.currentItem = 1
                }
                R.id.mRbDfw -> {
                    mPresenter.searchGoodsWords()
                    vpView.currentItem = 2
                }
            }
        }
        mPresenter.searchInfoWords()
    }

    private fun initData() {

    }

    private fun searchWord() {
        searchInfoFragment.refreshData()
        searchClassFragment.refreshData()
        searchGoodsFragment.refreshData()
    }
}
