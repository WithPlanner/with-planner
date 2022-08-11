package com.shop.withplanner.dto

data class CommunityInfo(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ResultX
)
data class ResultX(
    val communityImg: String,
    val createdAt: String,
    val currentCount: Int,
    val days: List<String>,
    val headCount: Int,
    val introduce: String,
    val name: String,
    val time: String,
    val type: String
)