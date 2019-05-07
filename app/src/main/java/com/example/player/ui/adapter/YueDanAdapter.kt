package com.example.player.ui.adapter

import android.content.Context
import com.example.player.base.BaseListAdapter
import com.example.player.model.PlayLists
import com.example.player.widget.YueDanItemView

/**
 * Created by Yuaihen.
 * on 2019/4/29
 * 悦单界面的Adapter
 */
class YueDanAdapter : BaseListAdapter<PlayLists, YueDanItemView>() {


    override fun refreshView(itemView: YueDanItemView, data: PlayLists) {
        itemView.setData(data)
    }

    override fun getItemView(context: Context?): YueDanItemView? {
        return YueDanItemView(context)
    }

}