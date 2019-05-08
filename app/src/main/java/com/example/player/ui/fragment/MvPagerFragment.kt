package com.example.player.ui.fragment

import com.example.player.base.BaseListAdapter
import com.example.player.base.BaseListFragment
import com.example.player.base.BaseListPresenter
import com.example.player.model.MvPagerBean
import com.example.player.model.VideoBean
import com.example.player.presenter.impl.MvListPresenterImpl
import com.example.player.ui.adapter.MvListAdapter
import com.example.player.widget.MvItemView

/**
 * Created by Yuaihen.
 * on 2019/5/8
 * MV每个界面的Fragment
 */
class MvPagerFragment : BaseListFragment<MvPagerBean, VideoBean, MvItemView>() {

    var code: String? = null

    override fun init() {
        code = arguments?.getString("args")
    }

    override fun getSpecialAdapter(): BaseListAdapter<VideoBean, MvItemView> {
        return MvListAdapter()
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return MvListPresenterImpl(code!!, this)
    }

    override fun getList(response: MvPagerBean?): List<VideoBean>? {
        return response?.videos
    }

//    override fun init() {
//        //通过Arguments传递数据
//        name = arguments?.getString("args")
//    }
//
//    override fun initView(): Int {
//        return R.layout.fragment_mv_pager
//    }
//
//    override fun initData() {
//        tv_fragment.text = name
//
//    }

}