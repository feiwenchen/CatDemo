package com.example.catfacts

import com.example.catfacts.ui.view.loadCallBack.EmptyCallback
import com.example.catfacts.ui.view.loadCallBack.ErrorCallback
import com.example.catfacts.ui.view.loadCallBack.LoadingCallback
import com.example.common_plugin.base.BaseApp
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir



class App : BaseApp() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())//加载
            .addCallback(ErrorCallback())//错误
            .addCallback(EmptyCallback())//空
            .setDefaultCallback(SuccessCallback::class.java)//设置默认加载状态页
            .commit()
    }

}
