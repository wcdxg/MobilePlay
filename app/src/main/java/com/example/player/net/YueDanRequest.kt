package com.example.player.net

import com.example.player.model.YueDanBean
import com.example.player.utils.URLProviderUtil

/**
 *   Create by hanjun
 *   on 2019-04-29
 *   悦单界面网络请求
 */
class YueDanRequest(offset: Int, handler: ResponseHandler<YueDanBean>) :
    MRequest<YueDanBean>(URLProviderUtil.getYueDanUrl(offset, 20), handler) {

}