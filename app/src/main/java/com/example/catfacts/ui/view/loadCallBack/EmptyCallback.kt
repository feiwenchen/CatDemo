package com.example.catfacts.ui.view.loadCallBack


import com.example.catfacts.R
import com.kingja.loadsir.callback.Callback


class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}