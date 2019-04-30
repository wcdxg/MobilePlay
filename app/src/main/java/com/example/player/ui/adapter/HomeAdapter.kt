package com.example.player.ui.adapter

import android.view.View
import android.view.ViewGroup
import com.example.player.model.HomeItemBean
import com.example.player.widget.HomeItemView
import com.example.player.widget.LoadMoreView

/**
 *   Create by hanjun
 *   on 2019-04-26
 *   首页Adapter
 */
class HomeAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    private var mData = ArrayList<HomeItemBean>()

    fun updateList(list: List<HomeItemBean>?) {
        list?.let {
            mData.clear()
            mData.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun loadMoreData(list: List<HomeItemBean>?) {
        list?.let {
            mData.addAll(list)
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return if (viewType == 1) {
            HomeHolder(LoadMoreView(parent.context))
        } else {
            HomeHolder(HomeItemView(parent.context))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mData.size) {
            //最后一条
            1
        } else {
            0
        }
    }

    override fun getItemCount(): Int {
        return mData.size + 1
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        if (position == mData.size) return
        val data = mData[position]
        val itemView = holder.itemView as HomeItemView
        itemView.setData(data)
    }


    class HomeHolder(itemView: View?) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!)
}
