package com.example.player.utils

import android.util.Log
import com.example.kotlin.BuildConfig

/**
 *   Create by hanjun
 *   on 2019-04-29
 */
class LogUtil private constructor() {

    private var isDebug: Boolean = BuildConfig.DEBUG

    companion object {
        val logUtil by lazy {
            LogUtil()
        }
    }

    fun v(tag: String = javaClass.simpleName, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    fun d(tag: String = javaClass.simpleName, msg: String) {
        if (isDebug)
            Log.d(tag, msg)
    }

    fun e(tag: String = javaClass.simpleName, msg: String) {
        if (isDebug)
            Log.e(tag, msg)
    }

    fun i(tag: String = javaClass.simpleName, msg: String) {
        if (isDebug)
            Log.i(tag, msg)
    }

    fun w(tag: String = javaClass.simpleName, msg: String) {
        if (isDebug)
            Log.w(tag, msg)
    }
}