package com.example.player.model

/**
 * Created by Yuaihen.
 * on 2019/5/8
 */

data class MvPagerBean(
    var totalCount: Int = 0,
    var videos: List<VideoBean> = listOf()
)

data class VideoBean(
    var albumImg: String = "",
    var artistName: String = "",
    var artists: List<ArtistBean> = listOf(),
    var description: String = "",
    var duration: Int = 0,
    var hdUrl: String = "",
    var hdVideoSize: Int = 0,
    var id: Int = 0,
    var linkId: Int = 0,
    var playListPic: String = "",
    var posterPic: String = "",
    var regdate: String = "",
    var shdUrl: String = "",
    var shdVideoSize: Int = 0,
    var status: Int = 0,
    var thumbnailPic: String = "",
    var title: String = "",
    var totalComments: Int = 0,
    var totalMobileViews: Int = 0,
    var totalPcViews: Int = 0,
    var totalViews: Int = 0,
    var traceUrl: String = "",
    var uhdUrl: String = "",
    var uhdVideoSize: Int = 0,
    var url: String = "",
    var videoSize: Int = 0,
    var videoSourceTypeName: String = ""
)

data class ArtistBean(
    var artistId: Int = 0,
    var artistName: String = ""
)
