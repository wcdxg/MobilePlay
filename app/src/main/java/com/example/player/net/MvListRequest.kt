package com.example.player.net

import com.example.player.utils.URLProviderUtil

/**
 * Created by Yuaihen.
 * on 2019/5/8
 * MV List请求
 */
class MvListRequest(args: String, offset: Int, handler: ResponseHandler<String>) :
    MRequest<String>(URLProviderUtil.getMVListUrl(args, offset, 20), handler) {

}