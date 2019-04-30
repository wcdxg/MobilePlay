package com.example.player.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.player.model.HomeItemBean
import com.example.player.model.PlayLists
import com.example.player.model.YueDanBean
import com.example.player.widget.LoadMoreView
import com.example.player.widget.YueDanItemView

/**
 * Created by Yuaihen.
 * on 2019/4/29
 * 悦单界面的Adapter
 */
class YueDanAdapter : RecyclerView.Adapter<YueDanAdapter.YueDanHolder>() {

    private var mData = ArrayList<PlayLists>()

    fun updateList(list: List<PlayLists>?) {
        list?.let {
            mData.clear()
            mData.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun loadMoreData(list: List<PlayLists>?) {
        list?.let {
            mData.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YueDanHolder {
        return if (viewType == 1) {
            YueDanHolder(LoadMoreView(parent.context))
        } else {
            YueDanHolder(YueDanItemView(parent.context))
        }
    }

    override fun getItemCount(): Int {
        return mData.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mData.size) {
            //最后一条
            1
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: YueDanHolder, position: Int) {
        if (position == mData.size) return
        val data = mData[position]

        val itemView = holder.itemView as YueDanItemView
        itemView.setData(data)
    }


    class YueDanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}