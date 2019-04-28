package com.example.player.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.player.ui.activity.MainActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 *   Create by hanjun
 *   on 2019/4/7
 *   所有Activity的基类
 */
abstract class BaseActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initListener()
        initData()
    }


    /**
     * 初始化数据
     */
    open protected fun initData() {

    }

    /**
     * 初始化监听器
     */
    open protected fun initListener() {

    }

    /**
     * 获取布局ID
     */
    abstract fun getLayoutId(): Int


    fun myToast(msg: String) {
        runOnUiThread {
            toast(msg)
        }
    }

   inline fun <reified T:BaseActivity>startActivityAndFinish(){
       startActivity<T>()
       finish()
   }

}
