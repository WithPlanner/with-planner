package com.shop.withplanner.dto

import com.google.gson.annotations.SerializedName

data class MyLoc (
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("roadAddress") val roadAddress: String? = null,
    @SerializedName("address") val address: String? = null,
    @SerializedName("alias") val alias: String? = null,
    @SerializedName("name") val name: String? = null,
        )