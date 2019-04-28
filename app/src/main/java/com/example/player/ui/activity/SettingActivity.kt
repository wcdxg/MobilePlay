package com.example.player.ui.activity

import android.preference.PreferenceManager
import com.example.kotlin.R
import com.example.player.base.BaseActivity
import com.example.player.utils.ToolBarManager
import kotlinx.android.synthetic.main.layout_tool_bar.*

/**
 *   Create by hanjun
 *   on 2019/4/8
 *   设置界面
 */
class SettingActivity : BaseActivity(),ToolBarManager {

    override val toolBar by lazy {
        tool_bar
    }


    override fun getLayoutId() = R.layout.activity_setting


    override fun initData() {
        initSettingToolBar()

        val pm = PreferenceManager.getDefaultSharedPreferences(this)
        val boolean = pm.getBoolean("push", false)
        println("push $boolean")
    }

}