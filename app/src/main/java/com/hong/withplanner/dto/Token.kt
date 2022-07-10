package com.hong.withplanner.dto

import com.google.gson.annotations.SerializedName

data class Token (
    @SerializedName("jwtToken")
    val token: String
)