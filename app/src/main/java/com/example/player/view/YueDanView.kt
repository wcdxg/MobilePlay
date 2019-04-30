package com.example.player.view

import com.example.player.model.YueDanBean

/**
 *   Create by hanjun
 *   on 2019-04-29
 *
 */
interface YueDanView {

    /**
     * 加载出错提示
     */
    fun onError(errorMsg: String?)

    /**
     * 加载成功
     * @param result 返回结果
     * @param isLoadMore 是加载更多还是初始化
     */
    fun loadSuccess(result: YueDanBean?, isLoadMore: Boolean)
}