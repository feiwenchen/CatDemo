package com.example.data_plugin.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class FactDetailResponse(
        @SerializedName("_id")
        var id: String,
        var user: FactUserResponse?,
        var text: String,
        var source: String,
        var type: String,
        var deleted: Boolean,
        var createdAt: String,
        var updatedAt: String) : Parcelable



