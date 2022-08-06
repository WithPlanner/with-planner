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
    var recommendList: List<RecommendList>,
    @Expose
    @SerializedName("myList")
    var myList: List<MyList>,
    @Expose
    @SerializedName("hotList")
    var hotList: List<HotList>,
    @Expose
    @SerializedName("newList")
    var newList: List<NewList>
)

data class NewList(
    @Expose
    @SerializedName("communityId")
    var communityId: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("communityImg")
    val communityImg: String
)

data class HotList(
    @Expose
    @SerializedName("communityId")
    var communityId: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("communityImg")
    val communityImg: String
)

data class MyList(
    @Expose
    @SerializedName("communityId")
    var communityId: Int,
    @Expose
    @SerializedName("name")
    val name: String
)

data class RecommendList(
    @Expose
    @SerializedName("communityId")
    var communityId: Int,
    @Expose
    @SerializedName("name")
    val name: String
)