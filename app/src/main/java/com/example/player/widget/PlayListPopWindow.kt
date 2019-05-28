package com.example.player.widget

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.PopupWindow
import com.example.kotlin.R
import org.jetbrains.anko.matchParent

/**
 * Created by Yuaihen.
 * on 2019/5/22
 * PopWindow
 */
class PlayListPopWindow(context: Context) : PopupWindow() {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.pop_playlist, null, false)
        contentView = view

        //设置宽高
        width = matchParent
        val manage = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        manage.defaultDisplay.getSize(point)
        val windowH = point.y
        height = (windowH * 3) / 5
        //设置获取焦点
        isFocusable = true
        //设置外部点击
        isOutsideTouchable = true
        //设置能够响应返回按钮
        setBackgroundDrawable(ColorDrawable())

    }
}