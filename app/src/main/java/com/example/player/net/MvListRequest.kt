package com.example.player.net

import com.example.player.model.MvPagerBean
import com.example.player.utils.URLProviderUtil

/**
 * Created by Yuaihen.
 * on 2019/5/8
 * MV List请求
 */
class MvListRequest(area: String, offset: Int, handler: ResponseHandler<MvPagerBean>) :
    MRequest<MvPagerBean>(URLProviderUtil.getMVListUrl(area, offset, 20), handler) {

}