package com.example.player.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.player.widget.YueDanItemView

/**
 * Created by Yuaihen.
 * on 2019/4/29
 * 悦单界面的Adapter
 */
class YueDanAdapter : RecyclerView.Adapter<YueDanAdapter.YueDanHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YueDanHolder {
        return YueDanHolder(YueDanItemView(parent.context))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: YueDanHolder, position: Int) {
        holder.itemView
    }


    class YueDanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}