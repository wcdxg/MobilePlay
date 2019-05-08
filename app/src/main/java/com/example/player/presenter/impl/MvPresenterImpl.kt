package com.example.player.presenter.impl

import com.example.player.base.BaseView
import com.example.player.model.MvAreaBean
import com.example.player.net.MvAreaRequest
import com.example.player.net.ResponseHandler
import com.example.player.presenter.interf.MvPresenter

/**
 * Created by Yuaihen.
 * on 2019/5/8
 */
class MvPresenterImpl(var mvView: BaseView<List<MvAreaBean>>?) : MvPresenter, ResponseHandler<List<MvAreaBean>> {

    override fun destory() {
        mvView?.let {
            mvView = null
        }
    }

    /**
     * 加载区域数据
     */
    override fun loadData(offset: Int, b: Boolean) {
        MvAreaRequest(this).excute()
    }

    override fun onError(msg: String?) {
        mvView?.onError(msg)
    }

    override fun onSuccess(result: List<MvAreaBean>) {
        mvView?.loadSuccess(result, false)
    }

}