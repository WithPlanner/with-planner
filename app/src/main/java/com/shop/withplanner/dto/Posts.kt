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
    val writerNickname: String
)