package com.example.catfacts.util

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.preference.PreferenceManager
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.example.catfacts.R
import com.example.catfacts.ui.view.loadCallBack.LoadingCallback
import com.kingja.loadsir.core.LoadService


object SettingUtil {

    fun setLoadingColor(color:Int,loadsir: LoadService<Any>) {
        loadsir.setCallBack(LoadingCallback::class.java) { _, view ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.findViewById<ProgressBar>(R.id.loading_progress).indeterminateTintMode = PorterDuff.Mode.SRC_ATOP
                view.findViewById<ProgressBar>(R.id.loading_progress).indeterminateTintList = getOneColorStateList(color)
            }
        }
    }

    private fun getOneColorStateList(color: Int): ColorStateList {
        val colors = intArrayOf(color)
        val states = arrayOfNulls<IntArray>(1)
        states[0] = intArrayOf()
        return ColorStateList(states, colors)
    }

    fun getOneColorStateList(context: Context): ColorStateList {
        val colors = intArrayOf(getColor(context))
        val states = arrayOfNulls<IntArray>(1)
        states[0] = intArrayOf()
        return ColorStateList(states, colors)
    }

    fun getColor(context: Context): Int {
        val setting = PreferenceManager.getDefaultSharedPreferences(context)
        val defaultColor = ContextCompat.getColor(context, R.color.colorPrimary)
        val color = setting.getInt("color", defaultColor)
        return if (color != 0 && Color.alpha(color) != 255) {
            defaultColor
        } else {
            color
        }

    }

    fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


}
