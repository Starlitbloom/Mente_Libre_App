package com.example.mente_libre_app.data.repository

import android.content.Context
import com.example.mente_libre_app.data.local.TokenManager
import com.example.mente_libre_app.data.remote.RemoteModule
import com.example.mente_libre_app.data.remote.api.AuthApi
import com.example.mente_libre_app.data.remote.dto.LoginRequestDto
import com.example.mente_libre_app.data.remote.dto.LoginResponseDto

class UserRepository(private val context: Context) {

    private val api = RemoteModule.create(AuthApi::class.java)

    // -------- LOGIN (nuevo, con microservicio) -----------
    suspend fun login(email: String, password: String): Result<LoginResponseDto> {
        return try {
            val response = api.login(LoginRequestDto(email, password))

            // guardar token
            TokenManager(context).saveToken(response.token)

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // -------- REGISTER (lo ajustaremos luego si tu backend lo soporta) ------
    suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String
    ): Result<Unit> {
        return Result.failure(Exception("Registro aún no implementado en el backend"))
    }
}
