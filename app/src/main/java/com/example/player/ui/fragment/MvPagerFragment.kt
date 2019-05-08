package com.example.player.ui.fragment

import com.example.kotlin.R
import com.example.player.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_mv_pager.*

/**
 * Created by Yuaihen.
 * on 2019/5/8
 * MV每个界面的Fragment
 */
class MvPagerFragment : BaseFragment() {

    var name: String? = null

    override fun init() {
        //通过Arguments传递数据
        name = arguments?.getString("args")
    }

    override fun initView(): Int {
        return R.layout.fragment_mv_pager
    }

    override fun initData() {
        tv_fragment.text = name

    }

}