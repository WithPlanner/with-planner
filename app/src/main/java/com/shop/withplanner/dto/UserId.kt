package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserId(
    @Expose
    @SerializedName("isSuccess")
    var isSuccess: Boolean,
    @Expose
    @SerializedName("code")
    var code: Int,
    @Expose
    @SerializedName("message")
    val message: String,
    @Expose
    @SerializedName("result")
    var result: UserIdResult
)

data class UserIdResult(
    @Expose
    @SerializedName("id")
    var id: Long,
    @Expose
    @SerializedName("msg")
    val msg: String
)
