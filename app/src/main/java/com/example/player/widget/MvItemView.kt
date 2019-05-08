package com.example.player.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.example.kotlin.R
import com.example.player.model.VideoBean

/**
 * Created by Yuaihen.
 * on 2019/5/8
 * MV每一个界面条目View
 */
class MvItemView : RelativeLayout {


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.item_mv, this)
    }

    fun setData(data: VideoBean) {

    }
}