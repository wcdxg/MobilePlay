package com.example.player.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Yuaihen.
 * on 2019/5/9
 * 视频播放列表的Bean 跳转用
 */
data class VideoPlayBean(
    var id: Int, var title: String,
    var desc: String, var thumPic: String,
    var url: String, var hdUrl: String, var uhdUrl: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeString(thumPic)
        parcel.writeString(url)
        parcel.writeString(hdUrl)
        parcel.writeString(uhdUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoPlayBean> {
        override fun createFromParcel(parcel: Parcel): VideoPlayBean {
            return VideoPlayBean(parcel)
        }

        override fun newArray(size: Int): Array<VideoPlayBean?> {
            return arrayOfNulls(size)
        }
    }


}