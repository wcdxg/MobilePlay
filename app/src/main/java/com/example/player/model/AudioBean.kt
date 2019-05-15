package com.example.player.model

import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore

/**
 * Created by Yuaihen.
 * on 2019/5/14
 * V榜Bean
 */
data class AudioBean(
    var data: String, var size: Long,
    var display_name: String, var artist: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(data)
        parcel.writeLong(size)
        parcel.writeString(display_name)
        parcel.writeString(artist)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AudioBean> {
        override fun createFromParcel(parcel: Parcel): AudioBean {
            return AudioBean(parcel)
        }

        override fun newArray(size: Int): Array<AudioBean?> {
            return arrayOfNulls(size)
        }

        fun getAudioBean(cursor: Cursor?): AudioBean {
            val bean = AudioBean("", 0, "", "")
            cursor?.let {
                bean.data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                bean.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
                var name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                bean.display_name = name.substring(0, name.lastIndexOf("."))
                bean.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            }
            return bean
        }


        fun getAudioBeans(cursor: Cursor?): ArrayList<AudioBean> {
            val list = arrayListOf<AudioBean>()
            cursor?.let {
                it.moveToPosition(-1)
                while (it.moveToNext()) {
                    val audioBean = getAudioBean(it)
                    list.add(audioBean)
                }
            }

            return list
        }
    }
}
