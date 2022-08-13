package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Posts(
    @Expose
    @SerializedName("postId")
    var postId: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("content")
    val content: String,
    @Expose
    @SerializedName("images")
    var images: List<Images>,
    @Expose
    @SerializedName("writerNickname")
    val writerNickname: String,
    @Expose
    @SerializedName("updatedAt")
    val updatedAt: String
)

data class MapPosts(
    @Expose
    @SerializedName("mapPostId")
    var mapPostId: Int,
    @Expose
    @SerializedName("userId")
    val userId: Int,
    @Expose
    @SerializedName("nickName")
    val nickName: String,
    @Expose
    @SerializedName("profileImg")
    var profileImg: String?,
    @Expose
    @SerializedName("location")
    val location: String,
    @Expose
    @SerializedName("updatedAt")
    val updatedAt: String
)