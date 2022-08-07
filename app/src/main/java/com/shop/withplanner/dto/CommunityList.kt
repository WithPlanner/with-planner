package com.shop.withplanner.dto

import android.graphics.Bitmap
import android.media.Image
import com.google.gson.annotations.SerializedName

data class CommunityList(
    @SerializedName("recommendList") val recommendList: List<Community>,
    @SerializedName("myList") val myList: List<Community>,
    @SerializedName("hotList") val hotList: List<Community>,
    @SerializedName("newList") val newList: List<Community>
)

data class Community (
    @SerializedName("communityId") val communityId: Int,
    @SerializedName("name") val name: String,
//    @SerializedName("communityImg")  val communityImg: Bitmap? = null
)
