package com.example.player.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.player.ui.fragment.VideoPagerFragment

/**
 * Created by Yuaihen.
 * on 2019/5/14
 * 视频播放界面PagerAdapter
 */
class VideoPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return VideoPagerFragment()
    }

    override fun getCount(): Int {
        return 3
    }

}