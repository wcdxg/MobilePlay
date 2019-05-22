package com.example.player.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.player.model.AudioBean
import org.greenrobot.eventbus.EventBus
import kotlin.random.Random

/**
 * Created by Yuaihen.
 * on 2019/5/15
 */
class AudioService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var list: ArrayList<AudioBean>? = null
    private var position = -2
    private val binder by lazy { AudioBinder() }
    private val sp by lazy { getSharedPreferences("config", Context.MODE_PRIVATE) }

    companion object {
        //播放模式
        val MODE_ALL = 1//全部循环
        val MODE_SINGLE = 2//单曲循环
        val MODE_RANDOM = 3//随机播放
        var mode = MODE_ALL//当前播放模式
    }

    override fun onCreate() {
        super.onCreate()
        mode = sp.getInt("mode", 1)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val pos = intent?.getIntExtra("position", 0) ?: 0
        if (pos != position) {
            position = pos
            list = intent?.getParcelableArrayListExtra<AudioBean>("list")
            binder.playItem()
        } else {
            binder.nofityUpdateUi()
        }

        // START_STICKY: service强制杀死之后，会尝试重新启动， 不会传递原来的intent
        // START_NOT_STICKY: service强制杀死之后，不会重新启动
        // START_REDELIVER_INTENT: service强制杀死之后，会尝试重新启动，会传递原来的intent，但对国产手机无效
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }


    inner class AudioBinder : Binder(), Iservice, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
        /**
         * 下一曲
         */
        override fun playNext() {
            list?.let {
                when (mode) {
                    MODE_RANDOM -> position = Random.nextInt(it.size)
                    else -> position = (position + 1) % it.size
                }

                playItem()
            }
        }

        /**
         * 上一曲
         */
        override fun playPre() {
            list?.let {
                when (mode) {
                    MODE_RANDOM -> position = Random.nextInt(it.size)
                    else -> if (position == 0) {
                        position = it.size - 1
                    } else {
                        position--
                    }
                }
                playItem()
            }
        }

        /**
         * 获取播放模式
         */
        override fun getPlayMode(): Int {
            return mode
        }

        /**
         * 修改播放模式
         */
        override fun updatePlayMode() {
            //ALL -> SINGLE->RANDOM
            when (mode) {
                MODE_ALL -> mode = MODE_SINGLE
                MODE_SINGLE -> mode = MODE_RANDOM
                MODE_RANDOM -> mode = MODE_ALL
            }

            sp.edit().putInt("mode", mode).apply()
        }

        override fun seekTo(progress: Int) {
            mediaPlayer?.seekTo(progress)
        }

        /**
         * 获取当前进度
         */
        override fun getProgress(): Int {
            return mediaPlayer?.currentPosition ?: 0
        }

        /**
         * 获取歌曲总时长
         */
        override fun getDuration(): Int {
            return mediaPlayer?.duration ?: 0
        }

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
         * 播放完成
         */
        override fun onCompletion(mp: MediaPlayer?) {
            //自动播放下一曲
            autoPlayNext()
        }

        /**
         * 根据播放模式自动播放下一曲
         */
        private fun autoPlayNext() {
            when (mode) {
                MODE_ALL -> list?.let { position = (position + 1) % it.size }
//                MODE_SINGLE ->
                MODE_RANDOM -> list?.let { position = Random.nextInt(it.size) }
            }

            playItem()
        }


        /**
         * 通知界面更新
         */
        fun nofityUpdateUi() {
            EventBus.getDefault().post(list?.get(position))
        }

        fun playItem() {
            mediaPlayer?.let {
                //先释放掉之前创建的mediaPlayer 避免多个音乐同时播放
                it.reset()
                it.release()
                mediaPlayer = null
            }
            mediaPlayer = MediaPlayer()
            mediaPlayer?.let {
                it.setDataSource(list?.get(position)?.data)
                it.prepareAsync()
                it.setOnPreparedListener(this)
                it.setOnCompletionListener(this)
            }
        }
    }

}