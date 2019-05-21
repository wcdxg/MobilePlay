package com.example.player.service

/**
 * Created by Yuaihen.
 * on 2019/5/15
 */
interface Iservice {

    fun updatePlayState()

    fun isPlaying(): Boolean?
    fun getDuration(): Int
    fun getProgress(): Int
    fun seekTo(progress: Int)
    fun updatePlayMode()
    fun getPlayMode():Int

}