package com.example.player.widget

import android.content.Context
import android.text.format.Formatter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.kotlin.R
import com.example.player.model.AudioBean
import kotlinx.android.synthetic.main.item_vbang.view.*

/**
 * Created by Yuaihen.
 * on 2019/5/14
 * V榜界面 itemView
 */
class VBangItemView : RelativeLayout {


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.item_vbang, this)
    }

    fun setData(itemBean: AudioBean) {
        val name = itemBean.display_name
        music_name.text = name.substring(0,name.lastIndexOf("."))
        artist.text = itemBean.artist
        size.text = Formatter.formatFileSize(context, itemBean.size)

    }
}