package com.example.player.model

import android.database.Cursor
import android.provider.MediaStore

/**
 * Created by Yuaihen.
 * on 2019/5/14
 * V榜Bean
 */
data class VBangBean(
    var data: String, var size: Long,
    var display_name: String, var artist: String
) {

    companion object {
        fun getVBangBean(cursor: Cursor?): VBangBean {
            val bean = VBangBean("", 0, "", "")
            cursor?.let {
                bean.data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                bean.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
                bean.display_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                bean.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            }
            return bean
        }
    }

}
