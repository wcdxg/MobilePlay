package com.example.player.ui.fragment

import com.example.player.base.BaseListAdapter
import com.example.player.base.BaseListFragment
import com.example.player.base.BaseListPresenter
import com.example.player.model.PlayLists
import com.example.player.model.YueDanBean
import com.example.player.presenter.impl.YueDanPresenterImpl
import com.example.player.ui.adapter.YueDanAdapter
import com.example.player.widget.YueDanItemView

/**
 *   Create by hanjun
 *   on 2019/4/10
 *   悦单界面
 */
class YueDanFragment : BaseListFragment<YueDanBean, PlayLists, YueDanItemView>() {

    override fun getSpecialAdapter(): BaseListAdapter<PlayLists, YueDanItemView> {
        return YueDanAdapter()
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return YueDanPresenterImpl(this)
    }

    override fun getList(response: YueDanBean?): List<PlayLists>? {
        return response?.playLists
    }

}