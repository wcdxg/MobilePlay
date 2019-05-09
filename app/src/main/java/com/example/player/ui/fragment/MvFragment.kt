package com.example.player.ui.fragment

import com.example.kotlin.R
import com.example.player.base.BaseFragment
import com.example.player.base.BaseView
import com.example.player.model.MvAreaBean
import com.example.player.presenter.impl.MvPresenterImpl
import com.example.player.ui.adapter.MvPagerAdapter
import kotlinx.android.synthetic.main.fragment_mv.*

/**
 *   Create by hanjun
 *   on 2019/4/10
 *   MV界面
 */
class MvFragment : BaseFragment(), BaseView<List<MvAreaBean>> {

    private val presenter by lazy {
        MvPresenterImpl(this)
    }

    override fun loadSuccess(response: List<MvAreaBean>?, isLoadMore: Boolean) {
//        myToast("加载区域数据成功")
        val adapter = MvPagerAdapter(context, response, childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onError(errorMsg: String?) {
        myToast("加载区域数据失败$errorMsg")
    }

    override fun initView(): Int {
        return R.layout.fragment_mv
    }


    override fun initListener() {

    }

    override fun initData() {
        presenter.loadData(0, false)
    }
}