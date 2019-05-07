package com.example.player.ui.fragment

import com.example.player.base.BaseListAdapter
import com.example.player.base.BaseListFragment
import com.example.player.base.BaseListPresenter
import com.example.player.model.HomeItemBean
import com.example.player.presenter.impl.HomePresenterImpl
import com.example.player.ui.adapter.HomeAdapter
import com.example.player.widget.HomeItemView

/**
 *   Create by hanjun
 *   on 2019/4/10
 *   首页
 */
class HomeFragment : BaseListFragment<List<HomeItemBean>, HomeItemBean, HomeItemView>() {

    override fun getSpecialAdapter(): BaseListAdapter<HomeItemBean, HomeItemView> {
        return HomeAdapter()
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return HomePresenterImpl(this)
    }

    override fun getList(response: List<HomeItemBean>?): List<HomeItemBean>? {
        return response
    }

}