package com.example.player.utils

import com.example.kotlin.R
import com.example.player.base.BaseFragment
import com.example.player.ui.fragment.HomeFragment
import com.example.player.ui.fragment.MvFragment
import com.example.player.ui.fragment.VBangFragment
import com.example.player.ui.fragment.YueDanFragment

/**
 *   Create by hanjun
 *   on 2019/4/10
 *   Fragment 管理类
 */
class FragmentUtil private constructor() { //单例 私有化构造

    val homeFragment by lazy { HomeFragment() }
    val mvFragment by lazy { MvFragment() }
    val vBangFragment by lazy { VBangFragment() }
    val yueDanFragment by lazy { YueDanFragment() }


    companion object {
        val fragmentUtil by lazy { FragmentUtil() }
    }


    /**
     * 根据tabId获取对应的Fragment
     */
    fun getFragment(id: Int): BaseFragment? {
        when (id) {
            R.id.tab_home -> return homeFragment
            R.id.tab_mv -> return mvFragment
            R.id.tab_vlist -> return vBangFragment
            R.id.tab_yue_dan ->return yueDanFragment

        }
        return null
    }
}