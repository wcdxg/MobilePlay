package com.example.player.ui.activity

import android.animation.Animator
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat
import androidx.core.view.ViewPropertyAnimatorListener
import android.view.View
import android.view.ViewPropertyAnimator
import com.example.kotlin.R
import com.example.player.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

/**
 *   Create by hanjun
 *   on 2019/4/7
 *   首页欢迎页
 */
class SplashActivity : BaseActivity(),ViewPropertyAnimatorListener{

    override fun onAnimationEnd(p0: View?) {
        startActivityAndFinish<MainActivity>()
    }

    override fun onAnimationCancel(p0: View?) {
    }

    override fun onAnimationStart(p0: View?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }


    override fun initData() {
        ViewCompat.animate(iv_banner)
            .scaleX(1.0f)
            .scaleY(1.0f)
            .setDuration(2000)
            .setListener(this)
    }


}
