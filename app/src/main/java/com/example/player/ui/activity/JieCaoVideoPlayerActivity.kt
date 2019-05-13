package com.example.player.ui.activity

import cn.jzvd.Jzvd
import com.example.kotlin.R
import com.example.player.base.BaseActivity
import com.example.player.model.VideoPlayBean
import com.example.player.utils.GlideUtil
import kotlinx.android.synthetic.main.activity_video_player_jiecao.*

/**
 * Created by Yuaihen.
 * on 2019/5/9
 */
class JieCaoVideoPlayerActivity : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_video_player_jiecao
    }

    override fun initData() {
        val data = intent.data
        if (data == null) {
            //获取传递的数据
            val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
            //应用内响应视频播放
            jiecao_videoView.setUp(videoPlayBean.url, videoPlayBean.title, Jzvd.SCREEN_NORMAL)
            GlideUtil.setUrl(this, videoPlayBean.thumPic, jiecao_videoView.thumbImageView)
        } else {
            //应用外响应视频播放
            if (data.toString().startsWith("http")) {
                //网络视频
                jiecao_videoView.setUp(data.toString(), data.toString(), Jzvd.SCREEN_NORMAL)
            } else {
                //本地视频
                jiecao_videoView.setUp(data.path, data.toString(), Jzvd.SCREEN_NORMAL)
            }

        }


    }


    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.resetAllVideos()
    }
}