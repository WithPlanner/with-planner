package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class  MyLocToSend(
    @Expose
    @SerializedName("longitude") val longitude: Double,
    @Expose
    @SerializedName("latitude") val latitude: Double,
    @Expose
    @SerializedName("roadAddress") val roadAddress: String? = null,
    @Expose
    @SerializedName("address") val address: String? = null,
    @Expose
    @SerializedName("alias") val alias: String? = null,
    @Expose
    @SerializedName("name") val name: String? = null,
)

data class MyLocToSendResponse(
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
    var result: MyLocToSendResult
)

data class MyLocToSendResult(
    @Expose
    @SerializedName("mapId") val mapId: Int,
    @Expose
    @SerializedName("longitude") val longitude: Double,
    @Expose
    @SerializedName("latitude") val latitude: Double,
    @Expose
    @SerializedName("roadAddress") val roadAddress: String? = null,
    @Expose
    @SerializedName("address") val address: String? = null,
    @Expose
    @SerializedName("alias") val alias: String? = null,
    @Expose
    @SerializedName("name") val name: String? = null,
)