package com.example.player.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.IBinder
import android.view.View
import com.example.kotlin.R
import com.example.player.base.BaseActivity
import com.example.player.model.AudioBean
import com.example.player.service.AudioService
import com.example.player.service.Iservice
import com.example.player.utils.StringUtil
import kotlinx.android.synthetic.main.activity_music_player_bottom.*
import kotlinx.android.synthetic.main.activity_music_player_middle.*
import kotlinx.android.synthetic.main.activity_music_player_top.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Yuaihen.
 * on 2019/5/15
 * 音乐播放界面
 */
const val MSG_UPDATE_PROGRESS = 0

class AudioPlayerActivity : BaseActivity(), View.OnClickListener {

    var audioBean: AudioBean? = null
    var drawable: AnimationDrawable? = null
    var duration = 0
    var mHandler = Handler {
        if (it.what == MSG_UPDATE_PROGRESS) {
            startUpdateDuration()
        }
        false
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.state -> updatePlayState()
            R.id.back -> finish()
        }
    }

    private fun updatePlayState() {
        iService?.updatePlayState()
        //更新播放按钮图标
        updatePlayStateBtn()
    }

    /**
     * 根据播放状态更新图标
     */
    private fun updatePlayStateBtn() {
        //获取播放状态
        val isPlaying = iService?.isPlaying()
        isPlaying?.let {
            if (it) {
                state.setImageResource(R.drawable.selector_btn_audio_play)
                drawable?.start()
            } else {
                state.setImageResource(R.drawable.selector_btn_audio_pause)
                drawable?.stop()
            }
        }
    }

    private val conn by lazy {
        AudioConnection()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }

    override fun initListener() {
        //播放状态切换
        state.setOnClickListener(this)
        back.setOnClickListener(this)
    }

    /**
     * 接收eventbus消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMainEvent(itemBean: AudioBean) {
        //设置歌曲名和歌手名
        this.audioBean = itemBean
        audio_title.text = itemBean.display_name
        artist.text = itemBean.artist
        //更新播放状态按钮
        updatePlayStateBtn()
        //动画播放
        drawable = audio_anim.drawable as AnimationDrawable
        drawable?.start()
        //获取总进度
        duration = iService?.getDuration() ?: 0
        //进度条设置最大值
        progress_sk.max = duration
        //更新播放进度
        startUpdateDuration()
    }

    /**
     * 开始更新进度条
     */
    private fun startUpdateDuration() {
        val progress = iService?.getProgress() ?: 0
        updateProgress(progress)
        //定时获取进度
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_PROGRESS, 1000)
    }

    /**
     * 根据当前进度更新界面
     */
    private fun updateProgress(pro: Int) {
        //更新进度数值
        progress.text = (StringUtil.parseDuration(pro) + "/").plus(StringUtil.parseDuration(duration))
    }


    override fun initData() {
        EventBus.getDefault().register(this)

        //直接传递intent
        val intent = intent
        intent.setClass(this, AudioService::class.java)
        startService(intent)
        //绑定服务
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    var iService: Iservice? = null

    inner class AudioConnection : ServiceConnection {
        //意外断开连接的时候
        override fun onServiceDisconnected(name: ComponentName?) {
        }

        //Service连接时
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iService = service as Iservice
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(conn)
        EventBus.getDefault().unregister(this)
        mHandler.removeCallbacksAndMessages(null)
    }

}