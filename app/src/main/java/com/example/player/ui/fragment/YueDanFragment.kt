package com.example.player.ui.fragment

import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import com.example.player.base.BaseFragment
import com.example.player.model.HomeItemBean
import com.example.player.model.YueDanBean
import com.example.player.presenter.impl.YueDanPresenterImpl
import com.example.player.ui.adapter.YueDanAdapter
import com.example.player.utils.ThreadUtil
import com.example.player.utils.URLProviderUtil
import com.example.player.view.YueDanView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_yuedan.*
import kotlinx.android.synthetic.main.fragment_yuedan.swipe_refresh
import okhttp3.*
import org.jetbrains.anko.support.v4.toast
import java.io.IOException

/**
 *   Create by hanjun
 *   on 2019/4/10
 *   悦单界面
 */
class YueDanFragment : BaseFragment(), YueDanView {
    override fun onError(errorMsg: String?) {
        swipe_refresh.isRefreshing = false
        toast("数据加载失败$errorMsg")

    }

    override fun loadSuccess(result: YueDanBean?, isLoadMore: Boolean) {
        toast("数据加载成功")
        swipe_refresh.isRefreshing = false
        if (isLoadMore) adapter.loadMoreData(result?.playLists)
        else adapter.updateList(result?.playLists)
    }


    private val adapter by lazy {
        YueDanAdapter()
    }

    private val presenter by lazy {
        YueDanPresenterImpl(this)
    }

    override fun initView(): Int {
        return R.layout.fragment_yuedan
    }


    override fun initListener() {
        recycler_yuedan.layoutManager = LinearLayoutManager(context)
        recycler_yuedan.adapter = adapter

        swipe_refresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
        swipe_refresh.setOnRefreshListener {
            presenter.loadData(0, false)
        }

        //上拉加载更多
        recycler_yuedan.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //RecyclerView停止滑动 并且显示的是最后一条
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val lastPosition = layoutManager.findLastVisibleItemPosition()
                        if (lastPosition == adapter.itemCount - 1) {
                            presenter.loadData(adapter.itemCount, true)
                        }
                    }
                }
            }
        })
    }


    override fun initData() {
        presenter.loadData(0, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destory()
    }
}