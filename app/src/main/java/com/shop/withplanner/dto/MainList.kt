package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MainList (
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
    var result: ResultMain
)

data class ResultMain(
    @Expose
    @SerializedName("recommendList")
    var recommendList: List<CommunityList>,
    @Expose
    @SerializedName("myList")
    var myList: List<CommunityList>,
    @Expose
    @SerializedName("hotList")
    var hotList: List<CommunityList>,
    @Expose
    @SerializedName("newList")
    var newList: List<CommunityList>
)