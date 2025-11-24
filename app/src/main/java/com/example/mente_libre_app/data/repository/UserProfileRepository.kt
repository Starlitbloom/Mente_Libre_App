package com.example.mente_libre_app.data.repository

import android.content.Context
import com.example.mente_libre_app.data.remote.core.RetrofitInstance
import com.example.mente_libre_app.data.remote.dto.AuthUserDto
import com.example.mente_libre_app.data.remote.dto.CreateUserProfileRequestDto
import com.example.mente_libre_app.data.remote.dto.UserProfileDto
import com.example.mente_libre_app.data.remote.dto.UserProfileCombinedDto

class UserProfileRepository(context: Context) {

    // API para UserProfileService
    private val profileApi = RetrofitInstance.getUserProfileService(context)

    // API para AuthService
    private val authApi = RetrofitInstance.getAuthService(context)

    // --- Obtener perfil completo combinando AuthService y UserProfileService ---
    suspend fun getFullUserProfile(userId: Long): Result<UserProfileCombinedDto> {
        return try {
            val perfil: UserProfileDto = profileApi.getProfileByUserId(userId)
            val auth = authApi.getUserById(userId)

            val combined = UserProfileCombinedDto(
                id = perfil.id,
                userId = userId,
                username = auth.username,
                email = auth.email,
                phone = auth.phone,
                direccion = perfil.direccion,
                fechaNacimiento = perfil.fechaNacimiento,
                notificaciones = perfil.notificaciones,
                generoId = perfil.generoId,
                generoNombre = perfil.generoNombre,
                fotoPerfil = perfil.fotoPerfil
            )

            Result.success(combined)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Obtener perfil solo del UserProfileService ---
    suspend fun getProfileById(id: Long): Result<UserProfileDto> {
        return try {
            Result.success(profileApi.getProfileById(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Crear perfil ---
    suspend fun createProfile(request: CreateUserProfileRequestDto): Result<UserProfileDto> {
        return try {
            Result.success(profileApi.createProfile(request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Actualizar perfil ---
    suspend fun updateProfile(id: Long, request: CreateUserProfileRequestDto): Result<UserProfileDto> {
        return try {
            Result.success(profileApi.updateProfile(id, request))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // --- Obtener datos de auth ---
    suspend fun getAuthUser(userId: Long): Result<AuthUserDto> {
        return try {
            Result.success(authApi.getUserById(userId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
