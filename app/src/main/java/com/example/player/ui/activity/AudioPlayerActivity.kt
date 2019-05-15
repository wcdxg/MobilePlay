package com.example.player.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.kotlin.R
import com.example.player.base.BaseActivity
import com.example.player.model.AudioBean
import com.example.player.service.AudioService

/**
 * Created by Yuaihen.
 * on 2019/5/15
 * 音乐播放界面
 */
class AudioPlayerActivity : BaseActivity() {

    private val conn by lazy {
        AudioConnection()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }


    override fun initData() {
        val list = intent.getParcelableArrayListExtra<AudioBean>("list")
        val position = intent.getIntExtra("position", 0)

        //通过audioService播放音乐
        val intent = Intent(this, AudioService::class.java)
        intent.putExtra("list", list)
        intent.putExtra("position", position)
        startService(intent)
        //绑定服务
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    inner class AudioConnection : ServiceConnection {
        //意外断开连接的时候
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        //Service连接时
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(conn)
    }

}