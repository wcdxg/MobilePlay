package com.example.player.net

import com.example.player.model.HomeItemBean
import com.example.player.utils.ThreadUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException

/**
 *   Create by hanjun
 *   on 2019-04-28
 *   网络请求管理类
 */
class NetManager private constructor() {

    companion object {
        val manager by lazy {
            NetManager()
        }
    }

   private val client by lazy {
        OkHttpClient()
    }


    /**
     * 发送网络请求
     */
    fun <T> sendRequest(req: MRequest<T>) {
        val request = Request.Builder()
            .url(req.url)
            .get()
            .build()
        client.newCall(request).enqueue(object : Callback {
            /**
             * 子线程
             */
            override fun onFailure(call: Call, e: IOException) {
                ThreadUtil.runOnMainThread(object : Runnable {
                    override fun run() {
                        req.handler.onError(e.message)
                    }

                })
            }

            /**
             * 子线程
             */
            override fun onResponse(call: Call, response: Response) {
                val result = response.body()?.string()
                val parseResult = req.parseResult(result)
                ThreadUtil.runOnMainThread(Runnable {
                    req.handler.onSuccess(parseResult)
                })

            }
        })
    }
}
