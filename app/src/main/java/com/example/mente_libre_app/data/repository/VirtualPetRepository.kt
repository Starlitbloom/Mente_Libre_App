package com.example.mente_libre_app.data.repository

import com.example.mente_libre_app.data.remote.api.VirtualPetApi
import com.example.mente_libre_app.data.remote.dto.virtualpet.CreatePetRequestDto
import com.example.mente_libre_app.data.remote.dto.virtualpet.PetResponseDto
import com.example.mente_libre_app.data.remote.dto.virtualpet.UpdatePetRequestDto

class VirtualPetRepository(
    private val api: VirtualPetApi
) {

    suspend fun createPet(dto: CreatePetRequestDto): Result<PetResponseDto> {
        return try {
            val res = api.createPet(dto)
            if (res.isSuccessful) Result.success(res.body()!!)
            else Result.failure(Exception("Error creando mascota: ${res.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMyPet(): Result<PetResponseDto> {
        return try {
            val res = api.getMyPet()
            if (res.isSuccessful) Result.success(res.body()!!)
            else Result.failure(Exception("Mascota no encontrada"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePet(dto: UpdatePetRequestDto): Result<PetResponseDto> {
        return try {
            val res = api.updatePet(dto)
            if (res.isSuccessful) Result.success(res.body()!!)
            else Result.failure(Exception("Error actualizando mascota"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addPoints(points: Int): Result<PetResponseDto> {
        return try {
            val res = api.addPoints(points)
            if (res.isSuccessful) Result.success(res.body()!!)
            else Result.failure(Exception("Error sumando puntos"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun restoreEnergy(): Result<PetResponseDto> {
        return try {
            val res = api.restoreEnergy()
            if (res.isSuccessful) Result.success(res.body()!!)
            else Result.failure(Exception("Error restaurando energía"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}