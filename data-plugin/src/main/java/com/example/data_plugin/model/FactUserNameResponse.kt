package com.example.data_plugin.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class FactUserNameResponse(
        var first: String,
        var last: String) : Parcelable



