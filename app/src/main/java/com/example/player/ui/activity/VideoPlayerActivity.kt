package com.example.player.ui.activity

import com.example.kotlin.R
import com.example.player.base.BaseActivity
import com.example.player.model.VideoPlayBean
import kotlinx.android.synthetic.main.activity_video_player.*

/**
 * Created by Yuaihen.
 * on 2019/5/9
 */
class VideoPlayerActivity : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_video_player
    }

    override fun initData() {
        //获取传递的数据
        val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")

        videoView.setVideoPath(videoPlayBean.url)
        videoView.start()
    }
}