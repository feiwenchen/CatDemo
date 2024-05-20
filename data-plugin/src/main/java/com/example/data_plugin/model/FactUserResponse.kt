package com.example.data_plugin.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class FactUserResponse(
        @SerializedName("_id")
        var id: String,
        var photo: String,
        var name: FactUserNameResponse) : Parcelable



