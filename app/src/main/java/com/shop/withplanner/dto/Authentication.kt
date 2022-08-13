package com.shop.withplanner.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthenticationRequest(
        @Expose
        @SerializedName("authenticationStatus")
        var authenticationStatus: Boolean,
        @Expose
        @SerializedName("localDateTime")
        var localDateTime: String,
)

data class Authentication (
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
        var result: AuthenticationResult
        )

data class AuthenticationResult(
        @Expose
        @SerializedName("saveStatus")
        var saveStatus: Boolean,
)