package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AutoLogin (
    @Expose
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @Expose
    @SerializedName("code")
    val code: Int,
    @Expose
    @SerializedName("message")
    val message: String
)