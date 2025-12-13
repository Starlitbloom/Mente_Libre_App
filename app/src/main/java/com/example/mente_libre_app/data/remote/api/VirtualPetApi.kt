package com.example.mente_libre_app.data.remote.api

import com.example.mente_libre_app.data.remote.dto.virtualpet.CreatePetRequestDto
import com.example.mente_libre_app.data.remote.dto.virtualpet.PetResponseDto
import com.example.mente_libre_app.data.remote.dto.virtualpet.UpdatePetRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VirtualPetApi {

    @POST("api/v1/pet/create")
    suspend fun createPet(
        @Body dto: CreatePetRequestDto
    ): Response<PetResponseDto>

    @GET("api/v1/pet/me")
    suspend fun getMyPet(): Response<PetResponseDto>

    @PUT("api/v1/pet/update")
    suspend fun updatePet(
        @Body dto: UpdatePetRequestDto
    ): Response<PetResponseDto>

    @PUT("api/v1/pet/add-points/{points}")
    suspend fun addPoints(
        @Path("points") points: Int
    ): Response<PetResponseDto>

    @PUT("api/v1/pet/energy/reduce/{amount}")
    suspend fun reduceEnergy(
        @Path("amount") amount: Int
    ): Response<PetResponseDto>

    @PUT("api/v1/pet/energy/restore")
    suspend fun restoreEnergy(): Response<PetResponseDto>
}