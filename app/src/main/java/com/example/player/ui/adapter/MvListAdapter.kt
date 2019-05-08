package com.example.player.ui.adapter

import android.content.Context
import com.example.player.base.BaseListAdapter
import com.example.player.model.VideoBean
import com.example.player.widget.MvItemView

/**
 * Created by Yuaihen.
 * on 2019/5/8
 * MV界面每个List列表的适配器
 */
class MvListAdapter : BaseListAdapter<VideoBean, MvItemView>() {

    override fun refreshView(itemView: MvItemView, data: VideoBean) {
        itemView.setData(data)
    }

    override fun getItemView(context: Context?): MvItemView? {
        return MvItemView(context)
    }

}