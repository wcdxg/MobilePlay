package com.example.player.ui.fragment

import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin.R
import com.example.player.base.BaseFragment
import com.example.player.ui.adapter.YueDanAdapter
import kotlinx.android.synthetic.main.fragment_yuedan.*

/**
 *   Create by hanjun
 *   on 2019/4/10
 *   悦单界面
 */
class YueDanFragment : BaseFragment() {


    private val adapter by lazy {
        YueDanAdapter()
    }

    override fun initView(): Int {
        return R.layout.fragment_yuedan
    }


    override fun initListener() {
        recycler_yuedan.layoutManager = LinearLayoutManager(context)
        recycler_yuedan.adapter = adapter

        swipe_refresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
        swipe_refresh.setOnRefreshListener {

        }
    }
}