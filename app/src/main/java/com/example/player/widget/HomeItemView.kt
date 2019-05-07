package com.example.player.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.example.kotlin.R
import com.example.player.model.HomeItemBean
import com.example.player.utils.MusicTypeUtil
import kotlinx.android.synthetic.main.item_home.view.*

/**
 *   Create by hanjun
 *   on 2019-04-26
 *   首页Item View
 */
class HomeItemView : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        View.inflate(context, R.layout.item_home, this)
    }


    /**
     * 设置数据
     */
    fun setData(data: HomeItemBean) {
        title.text = data.title
        description.text = data.description
        Glide.with(this).load(data.posterPic).into(mainPhoto)
        video_type.setImageResource(MusicTypeUtil.getType(data.type))
    }
}
