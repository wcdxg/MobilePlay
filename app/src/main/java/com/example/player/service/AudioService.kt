package com.example.player.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.player.model.AudioBean

/**
 * Created by Yuaihen.
 * on 2019/5/15
 */
class AudioService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private var list: ArrayList<AudioBean>? = null
    private var position = 0
    private val binder by lazy { AudioBinder() }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        list = intent?.getParcelableArrayListExtra<AudioBean>("list")
        position = intent?.getIntExtra("position", 0) ?: 0
        binder.playItem()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }


    inner class AudioBinder : Binder(), MediaPlayer.OnPreparedListener {
        override fun onPrepared(mp: MediaPlayer?) {
            mediaPlayer?.start()
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