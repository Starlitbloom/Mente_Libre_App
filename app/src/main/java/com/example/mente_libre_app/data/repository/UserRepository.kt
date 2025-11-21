package com.example.mente_libre_app.data.repository

import android.content.Context
import com.example.mente_libre_app.data.local.TokenManager
import com.example.mente_libre_app.data.remote.api.AuthApi
import com.example.mente_libre_app.data.remote.core.RetrofitInstance
import com.example.mente_libre_app.data.remote.dto.LoginRequestDto
import com.example.mente_libre_app.data.remote.dto.LoginResponseDto
import com.example.mente_libre_app.data.remote.dto.RegisterRequestDto

class UserRepository(private val context: Context) {

    private val api = RetrofitInstance.getAuthService(context)

    suspend fun login(email: String, password: String): Result<LoginResponseDto> {
        return try {
            val response = api.login(LoginRequestDto(email, password))

            TokenManager(context).saveToken(response.token)

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(request: RegisterRequestDto): Result<Unit> {
        return try {
            api.register(request)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

