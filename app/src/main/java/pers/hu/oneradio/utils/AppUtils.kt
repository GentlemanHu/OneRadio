package pers.hu.oneradio.utils

import android.util.Log

object AppUtils {
    @JvmStatic
    @JvmOverloads
    fun log(tag: String = "NO", s: String) {
        Log.println(Log.ERROR, tag, s)
    }
}