package com.example.player.presenter.impl

import com.example.player.base.BaseListPresenter
import com.example.player.base.BaseView
import com.example.player.model.MvPagerBean
import com.example.player.net.MvListRequest
import com.example.player.net.ResponseHandler

/**
 * Created by Yuaihen.
 * on 2019/5/8
 */
class MvListPresenterImpl(var area: String, var mvListView: BaseView<MvPagerBean>?) :
    BaseListPresenter, ResponseHandler<MvPagerBean> {

    //加载更多或者是初始化数据
    var isLoadMore: Boolean = false

    override fun onError(msg: String?) {
        mvListView?.onError(msg)
    }

    override fun onSuccess(result: MvPagerBean) {
        mvListView?.loadSuccess(result, isLoadMore)
    }

    override fun loadData(offset: Int, isLoadMore: Boolean) {
        this.isLoadMore = isLoadMore
        MvListRequest(area, 0, this).excute()
    }

    override fun destory() {
        mvListView?.let {
            mvListView = null
        }
    }

}