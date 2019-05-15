package com.example.player.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.player.model.AudioBean
import org.greenrobot.eventbus.EventBus

/**
 * Created by Yuaihen.
 * on 2019/5/15
 */
class AudioService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var list: ArrayList<AudioBean>? = null
    private var position = 0

    private val binder by lazy { AudioBinder() }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        list = intent?.getParcelableArrayListExtra<AudioBean>("list")
        position = intent?.getIntExtra("position", 0) ?: 0
        binder.playItem()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }


    inner class AudioBinder : Binder(), Iservice, MediaPlayer.OnPreparedListener {
        override fun updatePlayState() {
            //获取当前播放状态
            val isplaying = isPlaying()
            //切换播放状态
            isplaying?.let {
                if (it) {
                    //播放->暂停
                    mediaPlayer?.pause()
                } else {
                    //暂停-播放
                    mediaPlayer?.start()
                }
            }

        }

        override fun isPlaying(): Boolean? {
            return mediaPlayer?.isPlaying
        }

        override fun onPrepared(mp: MediaPlayer?) {
            mediaPlayer?.start()
            //通知界面更新
            nofityUpdateUi()
        }

        /**
         * 通知界面更新
         */
        private fun nofityUpdateUi() {
            EventBus.getDefault().post(list?.get(position))
        }

        fun playItem() {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.let {
                it.setOnPreparedListener(this)
                it.setDataSource(list?.get(position)?.data)
                it.prepareAsync()
            }
        }
    }

}