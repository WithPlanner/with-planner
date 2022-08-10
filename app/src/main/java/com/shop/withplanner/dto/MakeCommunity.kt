package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MakeCommunity (
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
    var result: ResultMakeCommunity
)

data class ResultMakeCommunity(
    @Expose
    @SerializedName("id")
    var id: Int,
    @Expose
    @SerializedName("msg")
    val msg: String
)