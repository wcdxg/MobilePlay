package com.example.player.ui.fragment

import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.database.Cursor
import android.os.AsyncTask
import android.provider.MediaStore
import com.example.kotlin.R
import com.example.player.base.BaseFragment
import com.example.player.ui.adapter.VBangAdapter
import com.example.player.utils.CursorUtil
import kotlinx.android.synthetic.main.fragment_vbang.*

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
                CursorUtil.logCursor(cursor)
                (cookie as VBangAdapter).swapCursor(cursor)
//                cursor.close()
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
    }

    override fun initData() {
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
}