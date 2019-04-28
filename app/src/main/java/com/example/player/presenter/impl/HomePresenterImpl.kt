package com.example.player.presenter.impl

import com.example.player.model.HomeItemBean
import com.example.player.net.HomeRequest
import com.example.player.net.ResponseHandler
import com.example.player.presenter.interf.HomePresenter
import com.example.player.view.HomeView

/**
 *   Create by hanjun
 *   on 2019-04-28
 */
class HomePresenterImpl(var homeView: HomeView?) : HomePresenter, ResponseHandler<List<HomeItemBean>> {

    /**
     * View和Presenter解绑
     */
    fun destory(){
        homeView?.let {
            homeView = null
        }
    }


    //加载更多或者是初始化数据
    var isLoadMore : Boolean = false

    override fun onError(msg: String?) {
        homeView?.onError(msg)
    }

    override fun onSuccess(result: List<HomeItemBean>) {
        homeView?.loadSuccess(result,isLoadMore)
    }


    /**
     * 初始化数据/刷新数据/加载更多
     * @param offset 从第多少条开始加载
     * @param isLoadMore 是否是加载更多
     */
    override fun loadData(offset: Int, isLoadMore: Boolean) {
        this.isLoadMore = isLoadMore
        HomeRequest(offset, this).excute()

//        NetManager.manager.sendRequest(request)
    }


//        val path = URLProviderUtil.getHomeUrl(offset, 20)
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url(path)
//            .get()
//            .build()
//        client.newCall(request).enqueue(object : Callback {
//            /**
//             * 子线程
//             */
//            override fun onFailure(call: Call, e: IOException) {
//                //加载失败隐藏刷新控件
//                ThreadUtil.runOnMainThread(object : Runnable {
//                    override fun run() {
////                        swipe_refresh.isRefreshing = false
//                        homeView.onError(e.message)
//                    }
//
//                })
//            }
//
//            /**
//             * 子线程
//             */
//            override fun onResponse(call: Call, response: Response) {
//                val result = response.body()?.string()
//                val gson = Gson()
//                val list =
//                    gson.fromJson<List<HomeItemBean>>(result, object : TypeToken<List<HomeItemBean>>() {}.type)
//
//                ThreadUtil.runOnMainThread(Runnable {
//                    homeView.loadSuccess(list, isLoadMore)
//                })
//
//            }
//        })
//    }


}
