package com.example.player.ui.activity

import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.view.Surface
import android.view.TextureView
import com.example.kotlin.R
import com.example.player.base.BaseActivity
import com.example.player.model.VideoPlayBean
import kotlinx.android.synthetic.main.activity_texture_video_player.*

/**
 * Created by Yuaihen.
 * on 2019/5/9
 * 使用TextureView代替VideoView
 * TextureView支持改变大小 旋转等
 */
class TextureVideoPlayerActivity : BaseActivity(), TextureView.SurfaceTextureListener {

    val mediaPlayer by lazy {
        MediaPlayer()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
        //窗体大小改变
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
        //视图更新
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        //关闭MediaPlayer
        mediaPlayer.let {
            mediaPlayer.stop()
            mediaPlayer.release()
        }


        //视图销毁
        return true
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        //视图可用
        videoPlayBean?.let {
            mediaPlayer.setDataSource(it.url)
            mediaPlayer.setSurface(Surface((surface)))//设置播放视频画面
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
//                texture_view.rotation = 100f
            }
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_texture_video_player
    }

    var videoPlayBean: VideoPlayBean? = null

    override fun initData() {
        //获取传递的数据
        videoPlayBean = intent.getParcelableExtra("item")

        texture_view.surfaceTextureListener = this
    }

}