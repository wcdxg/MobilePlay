package com.example.player.base

/**
 * Created by Yuaihen.
 * on 2019/4/30
 * 所有具有下拉刷新和上拉加载更多列表界面的Presenter基类
 */
interface BaseListPresenter {

    fun loadData(offset: Int, b: Boolean)

    //解绑Presenter和View的绑定
    fun destory()

}