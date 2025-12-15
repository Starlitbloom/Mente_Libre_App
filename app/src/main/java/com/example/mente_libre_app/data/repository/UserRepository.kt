package com.example.mente_libre_app.data.repository

import com.example.mente_libre_app.data.local.TokenDataStore
import com.example.mente_libre_app.data.remote.api.AuthApi
import com.example.mente_libre_app.data.remote.dto.auth.ChangePasswordRequestDto
import com.example.mente_libre_app.data.remote.dto.auth.LoginRequestDto
import com.example.mente_libre_app.data.remote.dto.auth.LoginResponseDto
import com.example.mente_libre_app.data.remote.dto.auth.RegisterRequestDto
import com.example.mente_libre_app.data.remote.dto.auth.UserResponseDto
import kotlinx.coroutines.flow.first

class UserRepository(
    private val tokenDataStore: TokenDataStore,
    private val api: AuthApi
) {

    suspend fun login(email: String, password: String): Result<LoginResponseDto> {
        return try {
            val response = api.login(LoginRequestDto(email, password))

            if (response.isSuccessful) {
                val body = response.body()!!

                tokenDataStore.saveToken(body.token)
                tokenDataStore.saveUserId(body.userId)

                Result.success(body)
            } else {
                Result.failure(Exception("Error en login: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(request: RegisterRequestDto): Result<UserResponseDto> {
        return try {
            val response = api.register(request)

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al registrar: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProfile(): Result<UserResponseDto> {
        return try {
            val token = tokenDataStore.tokenFlow.first()
                ?: return Result.failure(Exception("No hay token"))

            val response = api.getProfile("Bearer $token")

            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al obtener perfil: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        tokenDataStore.clearToken()
        tokenDataStore.clearUserId()
    }

    suspend fun getUserId(): Long? {
        return tokenDataStore.userIdFlow.first()
    }

    suspend fun saveToken(token: String) {
        tokenDataStore.saveToken(token)
    }

    suspend fun getToken(): String? {
        return tokenDataStore.tokenFlow.first()
    }

    suspend fun changePassword(actual: String, nueva: String): Result<Unit> {
        return try {
            val token = tokenDataStore.tokenFlow.first()
                ?: return Result.failure(Exception("No token"))

            val response = api.changePassword(
                "Bearer $token",
                ChangePasswordRequestDto(actual, nueva)
            )

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                // Si el backend devuelve mensaje en texto plano, lo leemos así:
                val backendMessage = response.errorBody()?.string()

                val msg = when (response.code()) {
                    400 -> backendMessage ?: "La contraseña actual es incorrecta"
                    401 -> "Sesión expirada, inicia sesión nuevamente"
                    else -> backendMessage ?: "Error inesperado (${response.code()})"
                }

                Result.failure(Exception(msg))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAuthAccount(): Result<Unit> {
        return try {
            val token = tokenDataStore.tokenFlow.first()
                ?: return Result.failure(Exception("No token"))

            val response = api.deleteMyAccount("Bearer $token")

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al eliminar cuenta (${response.code()})"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
