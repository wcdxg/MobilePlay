package com.example.player.ui.activity

import android.media.MediaPlayer
import com.example.kotlin.R
import com.example.player.base.BaseActivity
import com.example.player.model.AudioBean

/**
 * Created by Yuaihen.
 * on 2019/5/15
 * 音乐播放界面
 */
class AudioPlayerActivity : BaseActivity() {

    private val mediaPlayer by lazy {
        MediaPlayer()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }


    override fun initData() {
        val list = intent.getParcelableArrayListExtra<AudioBean>("list")
        val position = intent.getIntExtra("position", 0)
        //播放音乐
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }
        mediaPlayer.setDataSource(list[position].data)
        mediaPlayer.prepareAsync()
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}