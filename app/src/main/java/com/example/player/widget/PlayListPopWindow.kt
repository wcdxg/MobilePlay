package com.example.player.widget

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.example.kotlin.R
import org.jetbrains.anko.matchParent

/**
 * Created by Yuaihen.
 * on 2019/5/22
 * PopWindow 播放列表
 */
class PlayListPopWindow(context: Context, adapter: BaseAdapter, listener: AdapterView.OnItemClickListener, var window: Window) : PopupWindow() {

    private var alpha: Float = 0f

    init {
        alpha = window.attributes.alpha
        //保存窗体的透明度
        val view = LayoutInflater.from(context).inflate(R.layout.pop_playlist, null, false)
        contentView = view
        val listView = view.findViewById<ListView>(R.id.list_view)
        listView.adapter = adapter
        listView.onItemClickListener = listener
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
        //设置PopWindow动画
        animationStyle = R.style.pop

    }


    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        val attr = window.attributes
        attr.alpha = 0.4f
        window.attributes = attr
    }

    override fun dismiss() {
        super.dismiss()
        val attr = window.attributes
        attr.alpha = alpha
        window.attributes = attr
    }
}