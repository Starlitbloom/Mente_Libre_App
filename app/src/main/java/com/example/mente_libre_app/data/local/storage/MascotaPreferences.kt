package com.example.mente_libre_app.data.local.storage

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "mascota_prefs")

object MascotaPreferences {

    private val KEY_MASCOTA = stringPreferencesKey("mascota_elegida")
    private val KEY_MASCOTA_NOMBRE = stringPreferencesKey("mascota_nombre")

    suspend fun guardarMascota(context: Context, mascota: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_MASCOTA] = mascota
        }
    }

    fun obtenerMascota(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[KEY_MASCOTA]
        }
    }

    suspend fun guardarNombre(context: Context, nombre: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_MASCOTA_NOMBRE] = nombre
        }
    }

    fun obtenerNombre(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[KEY_MASCOTA_NOMBRE]
        }
    }
}
