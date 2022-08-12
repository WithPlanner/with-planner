package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommunityPostMain (
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
    var result: CommunityPostResult
)

data class CommunityPostResult(
    @Expose
    @SerializedName("communityId")
    var communityId: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("createdAt")
    val createdAt: String,
    @Expose
    @SerializedName("introduce")
    val introduce: String,
    @Expose
    @SerializedName("communityImg")
    val communityImg: String,
    @Expose
    @SerializedName("category")
    val category: String,
    @Expose
    @SerializedName("headCount")
    var headCount: Int,
    @Expose
    @SerializedName("currentCount")
    var currentCount: Int,
    @Expose
    @SerializedName("days")
    var days: List<String>,
    @Expose
    @SerializedName("time")
    val time: String,
    @Expose
    @SerializedName("posts")
    var posts: List<Posts>,
    @Expose
    @SerializedName("type")
    val type: String
)

data class CommunityMapPostMain (
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
    var result: CommunityMapPostResult
)

data class CommunityMapPostResult(
    @Expose
    @SerializedName("communityId")
    var communityId: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("introduce")
    val introduce: String,
    @Expose
    @SerializedName("communityImg")
    val communityImg: String,
    @Expose
    @SerializedName("category")
    val category: String,
    @Expose
    @SerializedName("headCount")
    var headCount: Int,
    @Expose
    @SerializedName("currentCount")
    var currentCount: Int,
    @Expose
    @SerializedName("createdAt")
    val createdAt: String,
    @Expose
    @SerializedName("updatedAt")
    val updatedAt: String,
    @Expose
    @SerializedName("time")
    val time: String,
    @SerializedName("days")
    var days: List<String>,
    @Expose
    @SerializedName("mapPosts")
    var mapPosts: List<MapPosts>,
)