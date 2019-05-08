package com.example.player.net

import com.example.player.model.MvAreaBean
import com.example.player.utils.URLProviderUtil

/**
 * Created by Yuaihen.
 * on 2019/5/8
 * MV区域数据请求
 */
class MvAreaRequest(handler: ResponseHandler<List<MvAreaBean>>) :
    MRequest<List<MvAreaBean>>(URLProviderUtil.getMVareaUrl(), handler) {

}