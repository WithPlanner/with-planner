package com.shop.withplanner.dto

data class LoginDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ResultLogin
)
data class ResultLogin(
    val jwtToken: String
)