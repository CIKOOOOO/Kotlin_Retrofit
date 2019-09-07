package com.andrew.kotlinlearning

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class Model(
    val image: Bitmap,
    val image_name: String,
    @SerializedName("response")
    val response: String
)