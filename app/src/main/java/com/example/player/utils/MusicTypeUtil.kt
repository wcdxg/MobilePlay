package com.example.player.utils

import com.example.kotlin.R

/**
 *   Create by hanjun
 *   on 2019-04-28
 *   根据名称或者对应Logo
 */
object MusicTypeUtil {


    fun getType(string: String): Int {
        return when (string) {
            "VIDEO" -> R.drawable.home_page_video
            "PROGRAM" -> R.drawable.home_page_program
            "ACTIVITY" -> R.drawable.home_page_activity
            "AD" -> R.drawable.home_page_ad
            "BULLETIN" -> R.drawable.home_page_bulletin
            "FANART" -> R.drawable.home_page_fanart
            "LIVE" -> R.drawable.home_page_live
            "PLAYLIST" -> R.drawable.home_page_playlist
            "PROJECT" -> R.drawable.home_page_project
            "STAR" -> R.drawable.home_page_star

            else -> return R.drawable.home_page_video
        }
    }
}
