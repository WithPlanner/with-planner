package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommunityList(
    @Expose
    @SerializedName("communityId")
    var communityId: Int,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("communityImg")
    val communityImg: String,
    @Expose
    @SerializedName("type")
    val type: String,
    @Expose
    @SerializedName("category")
    val category: String,
    @Expose
    @SerializedName("publicType")
    val publicType: String,

)
