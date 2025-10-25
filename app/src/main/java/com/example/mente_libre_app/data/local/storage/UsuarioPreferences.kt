package com.example.mente_libre_app.data.local.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 🔹 DataStore con nombre distinto
val Context.userDataStore by preferencesDataStore(name = "usuario_prefs")

object UsuarioPreferences {

    private val KEY_GENERO = stringPreferencesKey("genero_usuario")
    private val KEY_OBJETIVO = stringPreferencesKey("objetivo_usuario")
    private val KEY_FOTO = stringPreferencesKey("foto_usuario")
    suspend fun guardarGenero(context: Context, genero: String) {
        context.userDataStore.edit { prefs ->
            prefs[KEY_GENERO] = genero
        }
    }

    fun obtenerGenero(context: Context): Flow<String?> {
        return context.userDataStore.data.map { prefs ->
            prefs[KEY_GENERO]
        }
    }

    suspend fun guardarObjetivo(context: Context, objetivo: String) {
        context.userDataStore.edit { prefs ->
            prefs[KEY_OBJETIVO] = objetivo
        }
    }

    fun obtenerObjetivo(context: Context): Flow<String?> {
        return context.userDataStore.data.map { prefs ->
            prefs[KEY_OBJETIVO]
        }
    }
    suspend fun guardarFoto(context: Context, uri: String) {
        context.userDataStore.edit { prefs ->
            prefs[KEY_FOTO] = uri
        }
    }

    fun obtenerFoto(context: Context): Flow<String?> {
        return context.userDataStore.data.map { prefs -> prefs[KEY_FOTO] }
    }
}