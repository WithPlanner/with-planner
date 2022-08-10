package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class IdAndMsg(
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
    var result: IdAndMsgResult
)

data class IdAndMsgResult(
    @Expose
    @SerializedName("id")
    var id: Int,
    @Expose
    @SerializedName("msg")
    val msg: String
)
