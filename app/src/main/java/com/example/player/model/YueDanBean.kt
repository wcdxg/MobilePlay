package com.example.player.model

/**
 *   Create by hanjun
 *   on 2019-04-29
 */
data class YueDanBean(
    val playLists: List<PlayLists> = listOf(),
    val totalCount: Int = 0
)

data class PlayLists(
    val category: String = "",
    val createdTime: String = "",
    val creator: Creator = Creator(),
    val description: String = "",
    val id: Int = 0,
    val integral: Int = 0,
    val playListBigPic: String = "",
    val playListPic: String = "",
    val rank: Int = 0,
    val status: Int = 0,
    val thumbnailPic: String = "",
    val title: String = "",
    val totalFavorites: Int = 0,
    val totalUser: Int = 0,
    val totalViews: Int = 0,
    val updateTime: String = "",
    val videoCount: Int = 0,
    val weekIntegral: Int = 0
)

data class Creator(
    val largeAvatar: String = "",
    val nickName: String = "",
    val smallAvatar: String = "",
    val uid: Int = 0,
    val vipImg: String = "",
    val vipLevel: Int = 0
)