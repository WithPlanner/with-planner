package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JoinCommunity (
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
    var result: JoinCommunityResult
        )

data class JoinCommunityResult(
    @Expose
    @SerializedName("communityId")
    var communityId: Int,
    @Expose
    @SerializedName("userId")
    val userId: Int,
)