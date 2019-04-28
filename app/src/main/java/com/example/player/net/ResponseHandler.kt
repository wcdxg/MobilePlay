package com.example.player.net

/**
 *   Create by hanjun
 *   on 2019-04-28
 *   网络请求结果的回调
 */
interface ResponseHandler<T> {

    /**
     * 网络请求失败
     */
    fun onError(msg: String?)

    /**
     * 网络请求成功
     */
    fun onSuccess(result: T)
}