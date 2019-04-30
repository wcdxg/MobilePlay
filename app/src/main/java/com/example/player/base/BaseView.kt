package com.example.player.base

/**
 * Created by Yuaihen.
 * on 2019/4/30
 * 所有下拉刷新/上拉加载更多列表界面的基类
 */
interface BaseView<RESPONSE> {
    /**
     * 加载出错提示
     */
    fun onError(errorMsg: String?)

    /**
     * 加载成功
     * @param list 返回结果
     * @param isLoadMore 是加载更多还是初始化
     */
    fun loadSuccess(list: RESPONSE?, isLoadMore: Boolean)


}