package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostDetail (
    @Expose
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @Expose
    @SerializedName("code")
    val code: Int,
    @Expose
    @SerializedName("message")
    val message: String,
    @Expose
    @SerializedName("result")
    val result: PostDetailResult
)

data class PostDetailResult(
    @Expose
    @SerializedName("postId")
    val postId: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("content")
    val content: String,
    @Expose
    @SerializedName("images")
    val images: List<Images>,
    @Expose
    @SerializedName("writerNickname")
    val writerNickname: String,
    @Expose
    @SerializedName("comments")
    val comments: List<CommentResponseResult>,
    @Expose
    @SerializedName("authorStatus")
    val authorStatus: Boolean,
    @Expose
    @SerializedName("updatedAt")
    val updatedAt: String
)

data class MapPostDetail(
    @Expose
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @Expose
    @SerializedName("code")
    val code: Int,
    @Expose
    @SerializedName("message")
    val message: String,
    @Expose
    @SerializedName("result")
    val result: MapPostDetailResult
)

data class MapPostDetailResult(
    @Expose
    @SerializedName("mapPostId")
    val mapPostId: Int,
    @Expose
    @SerializedName("userId")
    val userId: Int,
    @Expose
    @SerializedName("nickName")
    val nickName: String,
    @Expose
    @SerializedName("location")
    val location: String,
    @Expose
    @SerializedName("comments")
    val comments: List<CommentResponseResult>,
    @Expose
    @SerializedName("updatedAt")
    val updatedAt: String
)