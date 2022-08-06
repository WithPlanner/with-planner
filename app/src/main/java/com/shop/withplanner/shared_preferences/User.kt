package com.shop.withplanner.shared_preferences

data class User (
    var name: String? = null,
    var nickname: String? = null,
    var email: String? = null,
    var password: String? = null,
    var token: String? = null
)