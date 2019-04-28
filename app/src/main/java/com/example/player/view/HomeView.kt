package com.example.player.view

import com.example.player.model.HomeItemBean

/**
 *   Create by hanjun
 *   on 2019-04-28
 *   Presenter和Home之间的交互
 */
interface HomeView {
    /**
     * 加载出错提示
     */
    fun onError(errorMsg: String?)

    /**
     * 加载成功
     * @param list 返回结果
     * @param isLoadMore 是加载更多还是初始化
     */
    fun loadSuccess(list: List<HomeItemBean>?, isLoadMore: Boolean)


}
