package com.example.player.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlin.R
import com.example.player.model.PlayLists
import com.example.player.utils.GlideUtil
import com.example.player.utils.LogUtil
import kotlinx.android.synthetic.main.item_yuedan.view.*

/**
 * Created by Yuaihen.
 * on 2019/4/29
 * 音悦RecyclerView item
 */
class YueDanItemView : RelativeLayout {


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_yuedan, this)
    }

    /**
     * 适配数据
     */
    fun setData(data: PlayLists) {
        GlideUtil.setUrl(context, data.playListBigPic, iv_bigPic)
        tv_title.text = data.title
        tv_count.text = data.videoCount.toString()
        tv_author.text = data.creator.nickName
        //头像开头有//斜杠
        val url = data.creator.largeAvatar.replace("//", "http://")
        LogUtil.d("url", url)
        Glide.with(context).load(url).apply(RequestOptions.circleCropTransform()).into(iv_author)

    }
}