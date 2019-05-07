package com.example.player.ui.adapter

import android.content.Context
import com.example.player.base.BaseListAdapter
import com.example.player.model.HomeItemBean
import com.example.player.widget.HomeItemView

/**
 *   Create by hanjun
 *   on 2019-04-26
 *   首页Adapter
 */
class HomeAdapter : BaseListAdapter<HomeItemBean, HomeItemView>() {


    override fun getItemView(context: Context?): HomeItemView? {
        return HomeItemView(context)
    }

    override fun refreshView(itemView: HomeItemView, data: HomeItemBean) {
        itemView.setData(data)
    }

}
