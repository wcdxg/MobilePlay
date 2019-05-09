package com.example.player.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.example.player.widget.LoadMoreView

/**
 * Created by Yuaihen.
 * on 2019/5/7
 * 所有具有下拉刷新和上拉加载更多列表界面Adapter
 */
abstract class BaseListAdapter<ITEMBEAN, ITEMVIEW : View> :
    androidx.recyclerview.widget.RecyclerView.Adapter<BaseListAdapter.BaseListHolder>() {

    private var mData = ArrayList<ITEMBEAN>()

    fun updateList(list: List<ITEMBEAN>?) {
        list?.let {
            mData.clear()
            mData.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun loadMoreData(list: List<ITEMBEAN>?) {
        list?.let {
            mData.addAll(list)
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListHolder {
        return if (viewType == 1) {
            BaseListHolder(LoadMoreView(parent.context))
        } else {
            BaseListHolder(getItemView(parent.context))
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

    override fun onBindViewHolder(holder: BaseListHolder, position: Int) {
        if (position == mData.size) return
        val data = mData[position]
        val itemView = holder.itemView as ITEMVIEW
//        itemView.setData(data)
        //条目刷新
        refreshView(itemView, data)
        //设置条目点击时间
        itemView.setOnClickListener {
            //第一种方式
//            listener?.let {
//                it(data)
//            }

            //第二种方式
            listener?.invoke(data)
        }
    }


    //Kotlin写法接口回调
    private var listener: ((itemBean: ITEMBEAN) -> Unit)? = null

    fun setMyItemListener(listener: (itemBean: ITEMBEAN) -> Unit) {
        this.listener = listener
    }

    //Java 写法接口回调
//    var listener: Listener<ITEMBEAN>? = null
//
//    interface Listener<ITEMBEAN> {
//        fun onItemClick(itemBean: ITEMBEAN)
//    }
//
//    fun setMyItemListener(listener: Listener<ITEMBEAN>) {
//        this.listener = listener
//    }


    class BaseListHolder(itemView: View?) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView!!)


    /**
     * 刷新条目
     */
    abstract fun refreshView(itemView: ITEMVIEW, data: ITEMBEAN)

    /**
     * 获取条目View
     */
    abstract fun getItemView(context: Context?): ITEMVIEW?
}