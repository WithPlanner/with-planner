package com.shop.withplanner.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import com.shop.withplanner.shared_preferences.PreferenceHelper.set
import com.shop.withplanner.shared_preferences.PreferenceHelper.get

class SharedManager(context:Context) {
    private val prefs: SharedPreferences = PreferenceHelper.defaultPrefs(context)

    fun saveCurrentUser(user: User) {
        prefs["name"] = user.name
        prefs["nickname"] = user.nickname
        prefs["email"] = user.email
        prefs["password"] = user.password
    }

    fun getCurrentUser(): User {
        return User().apply {
            nickname = prefs["nickname", ""]
            email = prefs["email", ""]
            password = prefs["password", ""]
        }
    }

    fun saveToken(token: String) {
        prefs["token"] = token
    }

    fun getToken(): String {
        return prefs["token", ""]
    }
}