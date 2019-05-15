package com.example.player.ui.adapter

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import com.example.player.model.AudioBean
import com.example.player.widget.VBangItemView

/**
 * Created by Yuaihen.
 * on 2019/5/14
 * V榜界面列表Adapter
 */
class VBangAdapter(context: Context?, c: Cursor?) : CursorAdapter(context, c) {

    /**
     * 创建条目View
     */
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return VBangItemView(context)
    }

    /**
     * 绑定View + data
     */
    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val itemView = view as VBangItemView
        val itemBean = AudioBean.getAudioBean(cursor)
        itemView.setData(itemBean)
    }

}