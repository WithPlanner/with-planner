package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Images(
    @Expose
    @SerializedName("createdAt")
    val createdAt: String,
    @Expose
    @SerializedName("updatedAt")
    val updatedAt: String,
    @Expose
    @SerializedName("id")
    var id: Int,
    @Expose
    @SerializedName("imgUrl")
    val imgUrl: String,
    @Expose
    @SerializedName("status")
    val status: String
)