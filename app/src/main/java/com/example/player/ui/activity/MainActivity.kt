package com.example.player.ui.activity

import com.example.player.base.BaseActivity
import com.example.player.utils.FragmentUtil
import com.example.player.utils.ToolBarManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_tool_bar.*


class MainActivity : BaseActivity(), ToolBarManager {


    override val toolBar by lazy {
        tool_bar
    }

    override fun getLayoutId(): Int {
        return com.example.kotlin.R.layout.activity_main
    }


    override fun initData() {
        initMainToolBar()
    }

    override fun initListener() {
        bottomBar.setOnTabSelectListener {
            //it -> tabId
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction
                .replace(
                    com.example.kotlin.R.id.fl_container,
                    FragmentUtil.fragmentUtil.getFragment(it)!!,
                    it.toString()
                )
                .commit()
        }
    }

}
