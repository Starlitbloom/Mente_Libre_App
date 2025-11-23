package com.example.mente_libre_app.data.local

import android.content.Context
import android.content.SharedPreferences

class TokenManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String, userId: Long? = null) {
        prefs.edit().apply {
            putString("token", token)
            userId?.let { putLong("user_id", it) }
            apply()
        }
    }

    fun getToken(): String? = prefs.getString("token", null)

    fun getUserId(): Long? {
        return if (prefs.contains("user_id")) prefs.getLong("user_id", -1) else null
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
