package com.example.player.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kotlin.R
import com.example.player.base.BaseActivity
import com.example.player.utils.FragmentUtil
import com.example.player.utils.ToolBarManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_tool_bar.*

class MainActivity : BaseActivity(), ToolBarManager {

    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override val toolBar by lazy {
        tool_bar
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun initData() {
        initMainToolBar()
        getPermission()
    }


    /**
     * 动态获取权限
     */
    private fun getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val i = ContextCompat.checkSelfPermission(this, permissions[0])
            if (i != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, 0)
            }
        }
    }


    override fun initListener() {
        bottomBar.setOnTabSelectListener {
            //it -> tabId
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction
                .replace(R.id.fl_container, FragmentUtil.fragmentUtil.getFragment(it)!!, it.toString())
                .commit()
        }
    }

}
