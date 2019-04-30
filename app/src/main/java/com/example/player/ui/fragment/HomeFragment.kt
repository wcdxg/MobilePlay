package com.example.player.ui.fragment

import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import com.example.player.base.BaseFragment
import com.example.player.model.HomeItemBean
import com.example.player.presenter.impl.HomePresenterImpl
import com.example.player.ui.adapter.HomeAdapter
import com.example.player.view.HomeView
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast

/**
 *   Create by hanjun
 *   on 2019/4/10
 *   首页
 */
class HomeFragment : BaseFragment(), HomeView {

   private val adapter by lazy {
        HomeAdapter()
    }

    private val presenter by lazy {
        HomePresenterImpl(this)
    }

    override fun initView(): Int {
        return R.layout.fragment_home
    }

    override fun initListener() {
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter

        //初始化下拉刷新控件
        swipe_refresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN)
        swipe_refresh.setOnRefreshListener {
            presenter.loadData(0, false)
        }

        //上拉加载更多
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

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


    override fun onError(errorMsg: String?) {
        toast("加载数据失败")
        swipe_refresh.isRefreshing = false
    }

    override fun loadSuccess(list: List<HomeItemBean>?, isLoadMore: Boolean) {
        toast("加载数据成功")
        swipe_refresh.isRefreshing = false
        if (isLoadMore) {
            //加载更多
            adapter.loadMoreData(list)
        } else {
            adapter.updateList(list)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destory()
    }

}