package com.example.common_plugin.base.activity

import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.common_plugin.base.viewmodel.BaseViewModel
import com.example.common_plugin.ext.inflateBindingWithGeneric

abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmActivity<VM>() {

    override fun layoutId() = 0

    private lateinit var mDatabind: DB

    /**
     * 创建DataBinding
     */
    override fun initDataBind(): View? {
        mDatabind = inflateBindingWithGeneric(layoutInflater)
        return mDatabind.root
    }
}