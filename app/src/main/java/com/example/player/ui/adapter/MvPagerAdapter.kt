package com.example.player.ui.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.player.model.MvAreaBean
import com.example.player.ui.fragment.MvPagerFragment

/**
 * Created by Yuaihen.
 * on 2019/5/8
 * MV界面Adapter
 */
class MvPagerAdapter(val context: Context?, val list: List<MvAreaBean>?, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        //Fragment传递数据第一种方式 Arguments
//        val fragment = MvPagerFragment()
        val bundle = Bundle()
        bundle.putString("args", list?.get(position)?.name)
//        fragment.arguments = bundle
        //Fragment传递数据第二种方式 instantiate
        val fragment = Fragment.instantiate(context, MvPagerFragment::class.java.name, bundle)

        return fragment
    }

    override fun getCount(): Int {
        //如果list不为null返回list.size 否则返回0
        return list?.size ?: 0
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return list?.get(position)?.name
    }
}
