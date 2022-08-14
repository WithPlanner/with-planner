package com.shop.withplanner.shared_preferences

import android.content.Context
import android.content.SharedPreferences
import com.shop.withplanner.dialog.MyLocDialog
import com.shop.withplanner.shared_preferences.PreferenceHelper.set
import com.shop.withplanner.shared_preferences.PreferenceHelper.get

class SharedManager(context: Context) {
    private val prefs: SharedPreferences = PreferenceHelper.defaultPrefs(context)

    fun saveCurrentUser(user: User) {
        prefs["token"] = user.token
    }

    fun saveToken(token: String) {
        prefs["token"] = token
    }

    fun getToken(): String {
        return prefs["token", ""]
    }
}