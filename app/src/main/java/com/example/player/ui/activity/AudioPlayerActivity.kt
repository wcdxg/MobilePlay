package com.example.player.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.IBinder
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import com.example.kotlin.R
import com.example.player.base.BaseActivity
import com.example.player.model.AudioBean
import com.example.player.service.AudioService
import com.example.player.service.Iservice
import com.example.player.ui.adapter.PopAdapter
import com.example.player.utils.StringUtil
import com.example.player.widget.PlayListPopWindow
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

class AudioPlayerActivity : BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemClickListener {

    /**
     * 弹出的播放列表的条目点击事件
     */
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //播放当前歌曲
        iService?.playPosition(position)
    }

    /**
     * @param progress 改变之后的进度
     * @param fromUser true用户手指拖动改变进度 false通过代码改变
     * */
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (!fromUser) return
        //更新播放进度
        iService?.seekTo(progress)
        //更新界面进度显示
        updateProgress(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

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
            R.id.playMode -> updatePlayMode()
            R.id.pre -> iService?.playPre()
            R.id.next -> iService?.playNext()
            R.id.playlist -> showPlayList()
        }
    }


    /**
     * 显示播放列表
     */
    private fun showPlayList() {
        val list = iService?.getPlayList()
        list?.let {
            //创建Adapter
            val adapter = PopAdapter(list)
            val popWindow = PlayListPopWindow(this, adapter, this, window)
            popWindow.showAsDropDown(audio_player_bottom, 0, -audio_player_bottom.height)
        }
    }

    /**
     * 更新播放模式图标
     */
    private fun updatePlayMode() {
        //修改Service中的模式
        iService?.updatePlayMode()
        //修改图标
        updatePlayModeBtn()
    }

    /**
     * 修改播放模式图标
     */
    private fun updatePlayModeBtn() {
        iService?.let {
            when (it.getPlayMode()) {
                AudioService.MODE_ALL -> playMode.setImageResource(R.drawable.selector_btn_playmode_order)
                AudioService.MODE_SINGLE -> playMode.setImageResource(R.drawable.selector_btn_playmode_single)
                AudioService.MODE_RANDOM -> playMode.setImageResource(R.drawable.selector_btn_playmode_random)
            }
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
        progress_sk.setOnSeekBarChangeListener(this)
        playMode.setOnClickListener(this)
        pre.setOnClickListener(this)
        next.setOnClickListener(this)
        playlist.setOnClickListener(this)
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
        //更新播放模式图标
        updatePlayModeBtn()
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
        progress_sk.progress = pro
    }


    override fun initData() {
        EventBus.getDefault().register(this)

        //直接传递intent
        val intent = intent
        intent.setClass(this, AudioService::class.java)

        //绑定服务
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
        //开启服务
        startService(intent)
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