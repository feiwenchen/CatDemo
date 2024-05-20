package com.example.catfacts.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.example.common_plugin.base.activity.BaseVmVbActivity
import com.example.common_plugin.base.viewmodel.BaseViewModel

abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : BaseVmVbActivity<VM, VB>() {

    abstract override fun initView(savedInstanceState: Bundle?)

    /**
     * 创建liveData观察者
     */
    override fun createObserver() {}

    /**
     * 打开等待框
     */
    override fun showLoading(message: String) {
    }

    /**
     * 关闭等待框
     */
    override fun dismissLoading() {
    }

}