package com.example.player.ui.fragment

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlin.R
import org.jetbrains.anko.toast

/**
 *   Create by hanjun
 *   on 2019/4/8
 *   设置界面Fragment
 */
@Suppress("DEPRECATION")
class SettingFragment : PreferenceFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        addPreferencesFromResource(R.xml.setting)
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
        //PreferenceFragment key点击事件
        val key = preference?.key
        if (key.equals("about")) {
            activity.toast("点击了关于")
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference)
    }
}