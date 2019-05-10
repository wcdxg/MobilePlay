package com.example.player.ui.activity

import com.example.kotlin.R
import com.example.player.base.BaseActivity
import com.example.player.model.VideoPlayBean
import kotlinx.android.synthetic.main.activity_video_player_ijk.*
import kotlinx.android.synthetic.main.activity_video_player_ijk.view.*

/**
 * Created by Yuaihen.
 * on 2019/5/9
 * IJKPlayer
 */
class IjkVideoPlayerActivity : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_video_player_ijk
    }

    override fun initData() {
        //获取传递的数据
//        var ijkMediaPlayer = IjkMediaPlayer()
//        ijkMediaPlayer.setOption(
//            IjkMediaPlayer.OPT_CATEGORY_FORMAT,
//            "protocol_whitelist",
//            "rtmp,crypto,file,http,https,tcp,tls,udp"
//        )
//
        val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
//        var urls = "http://120.76.228.112:8080/upload/videos/2016/09/09/1473147616256.mp4"
//        ijkVideoView.ijkVideoView.setVideoPath(urls)//异步的
        ijkVideoView.ijkVideoView.setVideoPath(videoPlayBean.url)//异步的
        ijkVideoView.setOnPreparedListener {
            ijkVideoView.start()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        ijkVideoView.stopPlayback()
    }
}