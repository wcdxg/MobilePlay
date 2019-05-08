package com.example.player.net

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType


/**
 *   Create by hanjun
 *   on 2019-04-28
 *   所有网络请求的基类
 *   @param RESPONSE 泛型类型
 */
open class MRequest<RESPONSE>(val url: String, val handler: ResponseHandler<RESPONSE>) {

    private val gson by lazy {
        Gson()
    }

    /**
     * 解析网络请求结果
     */
    fun parseResult(result: String?): RESPONSE {
        //获取 RESPONCE 的泛型类型
        val type = (this.javaClass
            .genericSuperclass as ParameterizedType).getActualTypeArguments()[0]

        return gson.fromJson(result, type)
    }


    /**
     * 直接执行网络请求
     */
    fun excute() {
        NetManager.manager.sendRequest(this)
    }
}