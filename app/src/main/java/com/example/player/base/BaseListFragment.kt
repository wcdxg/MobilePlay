package com.example.player.base

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by Yuaihen.
 * on 2019/4/30
 * 所有具有下拉刷新和上拉加载更多列表的基类
 * 需要抽取
 * HomeView->BaseView
 * Presenter->BasePresenter
 * HomeAdapter->BaseAdapter
 */
abstract class BaseListFragment<RESPONSE, ITEMBEAN, ITEMVIEW : View> : BaseFragment(), BaseView<RESPONSE> {

    private val adapter by lazy {
        getSpecialAdapter()
    }

    private val presenter by lazy {
        getSpecialPresenter()
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

    override fun loadSuccess(response: RESPONSE?, isLoadMore: Boolean) {
        toast("加载数据成功")
        swipe_refresh.isRefreshing = false
        if (isLoadMore) {
            //加载更多
            adapter.loadMoreData(getList(response))
        } else {
            adapter.updateList(getList(response))
        }
    }


    /**
     * 获取适配器Adapter
     */
    abstract fun getSpecialAdapter(): BaseListAdapter<ITEMBEAN, ITEMVIEW>

    /**
     * 获取Presenter
     */
    abstract fun getSpecialPresenter(): BaseListPresenter

    /**
     * 从返回结果中获取数据列表集合
     */
    abstract fun getList(response: RESPONSE?): List<ITEMBEAN>?

    override fun onDestroyView() {
        super.onDestroyView()
        //解绑presenter
        presenter.destory()
    }
}