package com.example.mente_libre_app.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_preferences")

class TokenDataStore(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val SHOW_WELCOME_POINTS = booleanPreferencesKey("show_welcome_points")
        private val LAST_MOOD_REWARD_DATE = stringPreferencesKey("last_mood_reward_date")
    }

    // TOKEN
    val tokenFlow: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[TOKEN_KEY]
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }

    // USER ID
    val userIdFlow: Flow<Long?> = context.dataStore.data.map { prefs ->
        prefs[USER_ID_KEY]?.toLong()
    }

    suspend fun saveUserId(id: Long) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = id.toString()
        }
    }

    suspend fun clearUserId() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
        }
    }


    val lastMoodRewardDateFlow: Flow<String?> =
        context.dataStore.data.map { prefs -> prefs[LAST_MOOD_REWARD_DATE] }

    suspend fun setLastMoodRewardDate(date: String) {
        context.dataStore.edit { prefs -> prefs[LAST_MOOD_REWARD_DATE] = date }
    }

}
