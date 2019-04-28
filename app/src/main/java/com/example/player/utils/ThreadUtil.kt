package com.example.player.utils

import android.os.Handler
import android.os.Looper

/**
 *   Create by hanjun
 *   on 2019-04-28
 *   运行在主线程中
 */
object ThreadUtil {

    private val handler = Handler(Looper.getMainLooper())

    fun runOnMainThread(runnable: Runnable) {
        handler.post(runnable)
    }

}