package com.shop.withplanner.shared_preferences

data class User (
    var token: String? = null,
    var name: String? = null,
    var nickname: String? = null,
    var email: String? = null,
    var password: String? = null
)