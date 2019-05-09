package com.example.player.ui.fragment

import com.example.player.base.BaseListAdapter
import com.example.player.base.BaseListFragment
import com.example.player.base.BaseListPresenter
import com.example.player.model.MvPagerBean
import com.example.player.model.VideoBean
import com.example.player.model.VideoPlayBean
import com.example.player.presenter.impl.MvListPresenterImpl
import com.example.player.ui.activity.VideoPlayerActivity
import com.example.player.ui.adapter.MvListAdapter
import com.example.player.widget.MvItemView
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by Yuaihen.
 * on 2019/5/8
 * MV每个界面的Fragment
 */
class MvPagerFragment : BaseListFragment<MvPagerBean, VideoBean, MvItemView>() {

    var code: String? = null

    val adapter by lazy {
        MvListAdapter()
    }

    override fun init() {
        code = arguments?.getString("args")
    }

    override fun getSpecialAdapter(): BaseListAdapter<VideoBean, MvItemView> {
        return adapter
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return MvListPresenterImpl(code!!, this)
    }

    override fun getList(response: MvPagerBean?): List<VideoBean>? {
        return response?.videos
    }

    override fun initListener() {
        super.initListener()
        //条目点击事件监听
        adapter.setMyItemListener {
            //TODO 标清 高清 超清 切换
            val videoPlayBean = VideoPlayBean(it.id, it.title, it.hdUrl, it.description)
            //跳转到视频播放界面
            startActivity<VideoPlayerActivity>("item" to videoPlayBean)
        }
    }

//    override fun init() {
//        //通过Arguments传递数据
//        name = arguments?.getString("args")
//    }
//
//    override fun initView(): Int {
//        return R.layout.fragment_mv_pager
//    }
//
//    override fun initData() {
//        tv_fragment.text = name
//
//    }

}