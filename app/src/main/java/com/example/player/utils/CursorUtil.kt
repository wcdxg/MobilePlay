package com.example.player.utils

import android.database.Cursor

/**
 * Created by Yuaihen.
 * on 2019/5/14
 * 游标打印工具类
 */
object CursorUtil {

    fun logCursor(cursor: Cursor) {
        cursor?.let {
            it.moveToPosition(-1)
            while (it.moveToNext()) {
                for (index in 0 until it.columnCount) {
                    println("Key=${it.getColumnName(index)} ---  Value=${it.getString(index)}")
                }
            }
        }
    }

}