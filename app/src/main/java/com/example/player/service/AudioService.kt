package com.example.player.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.kotlin.R
import com.example.player.model.AudioBean
import com.example.player.ui.activity.AudioPlayerActivity
import com.example.player.ui.activity.MainActivity
import org.greenrobot.eventbus.EventBus
import kotlin.random.Random

/**
 * Created by Yuaihen.
 * on 2019/5/15
 */
class AudioService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var list: ArrayList<AudioBean>? = null
    var manager: NotificationManager? = null
    var notifycation: Notification? = null
    private var position = -2
    private val binder by lazy { AudioBinder() }
    private val sp by lazy { getSharedPreferences("config", Context.MODE_PRIVATE) }

    companion object {
        //播放模式
        val MODE_ALL = 1//全部循环
        val MODE_SINGLE = 2//单曲循环
        val MODE_RANDOM = 3//随机播放
        var mode = MODE_ALL//当前播放模式

        val REQ_PRE = 10
        val REQ_STATE = 11
        val REQ_NEXT = 12
        val REQ_CONTENT = 13
        val FROM_PRE = 14
        val FROM_STATE = 15
        val FROM_NEXT = 16
        val FROM_CONTENT = 17
    }

    override fun onCreate() {
        super.onCreate()
        mode = sp.getInt("mode", 1)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //判断进入Service的方法
        val from = intent?.getIntExtra("from", -1)
        when (from) {
            FROM_PRE -> {
                binder.playPre()
            }
            FROM_STATE -> {
                binder.updatePlayState()
            }
            FROM_NEXT -> {
                binder.playNext()
            }
            FROM_CONTENT -> {
                binder.nofityUpdateUi()
            }
            else -> {
                val pos = intent?.getIntExtra("position", 0) ?: -1
                //与上一次播放的不一致 获取传递过来的数据
                if (pos != position) {
                    position = pos
                    list = intent?.getParcelableArrayListExtra<AudioBean>("list")
                    binder.playItem()
                } else {
                    binder.nofityUpdateUi()
                }

            }
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
         * 播放指定位置的歌曲
         */
        override fun playPosition(position: Int) {
            this@AudioService.position = position
            playItem()
        }

        /**
         * 获取播放集合
         */
        override fun getPlayList(): List<AudioBean>? {
            return list
        }

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

        /**
         * 更新播放状态
         */
        override fun updatePlayState() {
            //获取当前播放状态
            val isplaying = isPlaying()
            //切换播放状态
            isplaying?.let {
                if (it) {
                    //播放->暂停
                    pause()
                } else {
                    //暂停-播放
                    start()
                }
            }
        }


        /** 播放 */
        private fun start() {
            mediaPlayer?.start()
            EventBus.getDefault().post(list?.get(position))
            //更新通知图标
            notifycation?.contentView?.setImageViewResource(R.id.state, R.drawable.btn_audio_play_normal)
            //重新显示
            manager?.notify(1, notifycation)
        }

        /** 暂停 */
        private fun pause() {
            mediaPlayer?.pause()
            EventBus.getDefault().post(list?.get(position))
            notifycation?.contentView?.setImageViewResource(R.id.state, R.drawable.btn_audio_pause_normal)
            //重新显示
            manager?.notify(1, notifycation)
        }


        override fun isPlaying(): Boolean? {
            return mediaPlayer?.isPlaying
        }

        override fun onPrepared(mp: MediaPlayer?) {
            mediaPlayer?.start()
            //通知界面更新
            nofityUpdateUi()
            //显示通知
            showNotification()
        }

        /**
         * 显示通知
         */
        private fun showNotification() {
            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notifycation = getNotification()
            manager?.notify(1, notifycation)
        }

        private fun getNotification(): Notification? {
            //兼容Android 8.0通知
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val channel = NotificationChannel("001", "my_channel", NotificationManager.IMPORTANCE_DEFAULT)
                channel.enableLights(true)//是否在桌面icon右上角展示小红点
                channel.setLightColor(Color.GREEN) //小红点颜色
                channel.setShowBadge(true) //是否在久按桌面图标时显示此渠道的通知
                manager?.createNotificationChannel(channel)
            }

            return NotificationCompat.Builder(this@AudioService)
                .setTicker("正在播放歌曲${list?.get(position)?.display_name}")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(getRemoteViews())
                .setWhen(System.currentTimeMillis())    //设置通知栏时间
                .setOngoing(true)   //设置通知不能被移除
                .setContentIntent(getPendingIntent())
                .setChannelId("001")
                .setSound(null)
                .build()
        }


        /**
         * 点击通知主体事件
         */
        private fun getPendingIntent(): PendingIntent? {
            val intentM = Intent(this@AudioService, MainActivity::class.java)
            val intentA = Intent(this@AudioService, AudioPlayerActivity::class.java)
            intentA.putExtra("from", FROM_CONTENT)
            val intents = arrayOf(intentM, intentA)
            val pendingIntent = PendingIntent.getActivities(
                this@AudioService, REQ_CONTENT, intents, PendingIntent.FLAG_UPDATE_CURRENT
            )
            return pendingIntent
        }

        private fun getRemoteViews(): RemoteViews? {
            val remoteViews = RemoteViews(packageName, R.layout.notification)
            remoteViews.setTextViewText(R.id.title, list?.get(position)?.display_name)
            remoteViews.setTextViewText(R.id.artist, list?.get(position)?.artist)
            remoteViews.setOnClickPendingIntent(R.id.pre, getPrePendingIntent())
            remoteViews.setOnClickPendingIntent(R.id.state, getStatePendingIntent())
            remoteViews.setOnClickPendingIntent(R.id.next, getNextPendingIntent())
            return remoteViews
        }

        /**
         * 点击上一曲事件
         */
        private fun getPrePendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from", FROM_PRE)
            val pendingIntent = PendingIntent.getService(
                this@AudioService, REQ_PRE, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            return pendingIntent
        }

        /**
         * 点击暂停、播放事件
         */
        private fun getStatePendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from", FROM_STATE)
            val pendingIntent = PendingIntent.getService(
                this@AudioService, REQ_STATE, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            return pendingIntent
        }

        /**
         * 点击下一曲事件
         */
        private fun getNextPendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)
            intent.putExtra("from", FROM_NEXT)
            val pendingIntent = PendingIntent.getService(
                this@AudioService, REQ_NEXT, intent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            return pendingIntent
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