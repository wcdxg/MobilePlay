package com.example.player.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

/**
 *   Create by hanjun
 *   on 2019/4/7
 *   Fragment的基类
 */
abstract class BaseFragment : androidx.fragment.app.Fragment(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(initView(), container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
        initData()
    }

    protected open fun initData() {

    }

    protected open fun initListener() {

    }

    abstract fun initView(): Int

    protected open fun init() {

    }

    fun myToast(msg: String) {
        context?.runOnUiThread { toast(msg) }
    }


}