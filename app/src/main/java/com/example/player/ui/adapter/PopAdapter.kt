package com.example.player.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.player.model.AudioBean
import com.example.player.widget.PopListItemView

/**
 *   Create by hanjun
 *   on 2019-05-28
 *   PopWindow播放列表适配器
 */
class PopAdapter(var list: List<AudioBean>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView: PopListItemView?  = null

        itemView = if (convertView == null) {
            PopListItemView(parent?.context)
        } else {
            convertView as PopListItemView
        }

        itemView.setData(list.get(position))
        return itemView
    }

    override fun getItem(position: Int): Any {
        return list.get(position)
    }


    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

}