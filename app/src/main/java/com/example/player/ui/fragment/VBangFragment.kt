package com.example.player.ui.fragment

import com.example.kotlin.R
import com.example.player.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_vbang.*

/**
 *   Create by hanjun
 *   on 2019/4/10
 *   V榜界面
 */
class VBangFragment : BaseFragment() {


    override fun initView(): Int {
        return R.layout.fragment_vbang
    }

    override fun initListener() {
        listView.adapter
    }

}