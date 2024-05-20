package com.example.common_plugin.base.activity

import android.view.View
import androidx.viewbinding.ViewBinding
import com.example.common_plugin.base.viewmodel.BaseViewModel
import com.example.common_plugin.ext.inflateBindingWithGeneric

abstract class BaseVmVbActivity<VM : BaseViewModel, VB : ViewBinding> : BaseVmActivity<VM>() {

    override fun layoutId(): Int = 0

    lateinit var mViewBind: VB

    /**
     * 创建DataBinding
     */
    override fun initDataBind(): View? {
        mViewBind = inflateBindingWithGeneric(layoutInflater)
        return mViewBind.root

    }
}