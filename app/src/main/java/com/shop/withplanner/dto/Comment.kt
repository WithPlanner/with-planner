package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommentResponse(
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
    val result: CommentResponseResult
)

data class CommentResponseResult(
    @Expose
    @SerializedName("commentId")
    val commentId: Int,
    @Expose
    @SerializedName("nickname")
    val nickname: String,
    @Expose
    @SerializedName("comment")
    val comment: String,
    @Expose
    @SerializedName("createdAt")
    val createdAt: String
)
