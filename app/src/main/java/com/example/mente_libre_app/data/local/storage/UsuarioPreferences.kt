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

    private val KEY_LOGGED_IN = stringPreferencesKey("is_logged_in") // "true"/"false"
    private val KEY_NOMBRE = stringPreferencesKey("nombre_usuario")
    private val KEY_EMAIL = stringPreferencesKey("email_usuario")
    private val KEY_PHONE = stringPreferencesKey("phone_usuario")
    private val KEY_CUMPLEANOS = stringPreferencesKey("cumpleanos_usuario")
    private val KEY_LATITUD = stringPreferencesKey("latitud_usuario")
    private val KEY_LONGITUD = stringPreferencesKey("longitud_usuario")

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

    suspend fun guardarSesion(context: Context, loggedIn: Boolean) {
        context.userDataStore.edit { prefs ->
            prefs[KEY_LOGGED_IN] = loggedIn.toString()
        }
    }

    fun obtenerSesion(context: Context): Flow<Boolean> {
        return context.userDataStore.data.map { prefs ->
            prefs[KEY_LOGGED_IN]?.toBoolean() ?: false
        }
    }

    suspend fun cerrarSesion(context: Context) {
        context.userDataStore.edit { prefs ->
            prefs[KEY_LOGGED_IN] = false.toString()
        }
    }

    suspend fun guardarNombre(context: Context, nombre: String) {
        context.userDataStore.edit { prefs ->
            prefs[KEY_NOMBRE] = nombre
        }
    }

    fun obtenerNombre(context: Context): Flow<String?> {
        return context.userDataStore.data.map { prefs -> prefs[KEY_NOMBRE] }
    }

    suspend fun guardarEmail(context: Context, email: String) {
        context.userDataStore.edit { prefs -> prefs[KEY_EMAIL] = email }
    }

    fun obtenerEmail(context: Context): Flow<String?> {
        return context.userDataStore.data.map { prefs -> prefs[KEY_EMAIL] }
    }

    suspend fun guardarTelefono(context: Context, telefono: String) {
        context.userDataStore.edit { prefs -> prefs[KEY_PHONE] = telefono }
    }

    fun obtenerTelefono(context: Context): Flow<String?> {
        return context.userDataStore.data.map { prefs -> prefs[KEY_PHONE] }
    }

    suspend fun guardarCumpleanos(context: Context, fecha: String) {
        context.userDataStore.edit { prefs ->
            prefs[KEY_CUMPLEANOS] = fecha
        }
    }

    fun obtenerCumpleanos(context: Context): Flow<String?> {
        return context.userDataStore.data.map { prefs -> prefs[KEY_CUMPLEANOS] }
    }

    suspend fun guardarUbicacion(context: Context, latitud: Double, longitud: Double) {
        context.userDataStore.edit { prefs ->
            prefs[KEY_LATITUD] = latitud.toString()
            prefs[KEY_LONGITUD] = longitud.toString()
        }
    }

    fun obtenerUbicacion(context: Context): Flow<Pair<Double?, Double?>> {
        return context.userDataStore.data.map { prefs ->
            val lat = prefs[KEY_LATITUD]?.toDoubleOrNull()
            val lon = prefs[KEY_LONGITUD]?.toDoubleOrNull()
            lat to lon
        }
    }

}