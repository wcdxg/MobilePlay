package com.example.player.utils

import androidx.appcompat.widget.Toolbar
import com.example.kotlin.R
import com.example.player.ui.activity.SettingActivity
import org.jetbrains.anko.startActivity

/**
 *   Create by hanjun
 *   on 2019/4/8
 *   ToolBar的管理类
 */
interface ToolBarManager {

    val toolBar: Toolbar

    /**
     * 初始化主界面的Toolbar
     */
    fun initMainToolBar() {
        toolBar.title = "网易云音乐"
        toolBar.inflateMenu(R.menu.main)


        //Koltin调用java接口特性:如果接口内未实现的方法只有一个,可以省略接口对象,直接复写方法
        toolBar.setOnMenuItemClickListener {
            toolBar.context.startActivity<SettingActivity>()
            true
        }


        //匿名内部类用object
//        toolBar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
//            override fun onMenuItemClick(item: MenuItem?): Boolean {
//                when (item?.itemId) {
//                    R.id.settting -> {
//                        //跳转到设置界面
//                        toolBar.context.startActivity<SettingActivity>() //Anko写法
//
//                        //kotlin原生写法
////                        toolBar.context.startActivity(Intent(toolBar.context, SettingActivity::class.java))
//                    }
//                }
//                return true
//            }
//        })
    }


    /**
     * 初始化设置界面的toolbar
     */
    fun initSettingToolBar() {
        toolBar.title = "设置"
    }
}
