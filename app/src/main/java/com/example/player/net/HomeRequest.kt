package com.example.player.net

import com.example.player.model.HomeItemBean
import com.example.player.utils.URLProviderUtil

/**
 *   Create by hanjun
 *   on 2019-04-28
 *   首页获取数据
 */
class HomeRequest(offset: Int, handler: ResponseHandler<List<HomeItemBean>>) :
    MRequest<List<HomeItemBean>>(URLProviderUtil.getHomeUrl(offset, 20), handler) {

}