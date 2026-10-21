package com.fit2081.nutritrack_pro_elijah_chin_35681756.data

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

object AuthManager {

    private const val PREFS_NAME = "authmanager_prefs"
    private const val USER_ID = "user_id"

    private lateinit var pref: android.content.SharedPreferences

    val _userId: MutableState<String?> = mutableStateOf(null)

    fun checkId(context: Context) {
        pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        _userId.value = pref.getString(USER_ID, null)
    }

    fun login(userId: String) {
        _userId.value = userId
        pref.edit().putString(USER_ID, userId).apply()
    }

    fun logout() {
        _userId.value = "null"
        pref.edit().remove(USER_ID).apply()
    }

    fun getUserId(): String? {
        return _userId.value
    }
}