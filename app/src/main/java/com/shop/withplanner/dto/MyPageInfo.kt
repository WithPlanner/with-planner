package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MyPageInfo(
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
    var result: MyPageResult
)

data class MyPageResult(
    @Expose
    @SerializedName("userId")
    var userId: Int,
    @Expose
    @SerializedName("nickname")
    val nickname: String,
    @Expose
    @SerializedName("email")
    val email: String,
    @Expose
    @SerializedName("communities")
    var communities: List<CommunityList>
)
