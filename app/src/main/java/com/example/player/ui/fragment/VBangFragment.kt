package com.example.player.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.AsyncTask
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import com.example.kotlin.R
import com.example.player.base.BaseFragment
import com.example.player.model.AudioBean
import com.example.player.ui.activity.AudioPlayerActivity
import com.example.player.ui.adapter.VBangAdapter
import com.example.player.utils.CursorUtil
import kotlinx.android.synthetic.main.fragment_vbang.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.yesButton

/**
 *   Create by hanjun
 *   on 2019/4/10
 *   V榜界面
 */
class VBangFragment : BaseFragment() {

    private class MyHandler(resolver: ContentResolver) : AsyncQueryHandler(resolver) {
        override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
            //查询完成后 主线程
            cursor?.let {
//                CursorUtil.logCursor(cursor)
                (cookie as VBangAdapter).swapCursor(cursor)
            }
        }
    }

    private val audioTask by lazy {
        AudioTask()
    }


    override fun initView(): Int {
        return R.layout.fragment_vbang
    }

    private var adapter: VBangAdapter? = null

    override fun initListener() {
        adapter = VBangAdapter(context, null)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            //跳转到音乐播放界面
            val cursor: Cursor = adapter?.getItem(position) as Cursor
            val list = AudioBean.getAudioBeans(cursor)
            startActivity<AudioPlayerActivity>("list" to list, "position" to position)
        }
    }

    override fun initData() {
        handlerPermission()
    }

    /**
     * 申请读写权限
     */
    private fun handlerPermission() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val checkSelfPermission = ActivityCompat.checkSelfPermission(context!!, permission)
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
            //已经获取到权限
            loadSong()
        } else {
            //没有获取权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)) {
                //需要弹出
                alert("网易云音乐需要获取读取权限以访问您的音乐文件!", "温馨提示") {
                    yesButton { myRequestPermission() }
                    noButton { }
                }.show()
            } else {
                //不需要弹出
                myRequestPermission()
            }
        }


    }

    /**
     * 真正申请权限的操作
     */
    private fun myRequestPermission() {
        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        requestPermissions(permissions, 1)

    }


    /**
     * 接收权限授权结果
     * requestCode 请求码
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadSong()
        }
    }

    private fun loadSong() {
        val resolver = context?.contentResolver
        //AysncTask方式查询
//        audioTask.execute(resolver)

        //Handler方式查询
        resolver?.let {
            MyHandler(resolver).startQuery(
                0, adapter, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.ARTIST
                ), null, null, null
            )
        }
    }


    /**
     * 音乐查询异步任务 并回调到主线程中展示
     */
    class AudioTask : AsyncTask<ContentResolver, Void, Cursor>() {

        override fun doInBackground(vararg params: ContentResolver?): Cursor? {
            return params[0]?.let {
                it.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    arrayOf(
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.ARTIST
                    ), null, null, null
                )
            }
        }

        override fun onPostExecute(cursor: Cursor?) {
            cursor?.let {
                CursorUtil.logCursor(cursor)
                cursor.close()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //关闭cursor
        adapter?.changeCursor(null)
    }
}